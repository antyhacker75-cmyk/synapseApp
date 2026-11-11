package com.mysketch.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.mysketch.shared.data.local.database.CachedConversationDao
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.local.database.PostDao
import com.mysketch.shared.data.local.database.StoryDao
import com.mysketch.shared.data.sync.AccountSyncManager
import com.mysketch.shared.data.sync.SyncManager
import com.mysketch.shared.domain.model.auth.AuthSessionStatus
import com.mysketch.shared.domain.model.auth.SocialProvider
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.repository.OfflineActionRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Firebase Authentication owns email/password credentials.
 * Firebase Realtime Database stores account, profile and username-index data.
 * New account data is written only after the email address is verified.
 */
class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(),
    private val cachedMessageDao: CachedMessageDao? = null,
    private val cachedConversationDao: CachedConversationDao? = null,
    private val postDao: PostDao? = null,
    private val storyDao: StoryDao? = null,
    private val offlineActionRepository: OfflineActionRepository? = null,
    private val syncManager: SyncManager? = null
) : AuthRepository {

    override val sessionStatus: Flow<AuthSessionStatus> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(
                if (firebaseAuth.currentUser?.isEmailVerified == true) {
                    AuthSessionStatus.AUTHENTICATED
                } else {
                    AuthSessionStatus.NOT_AUTHENTICATED
                }
            )
        }
        trySend(
            if (auth.currentUser?.isEmailVerified == true) AuthSessionStatus.AUTHENTICATED
            else AuthSessionStatus.INITIALIZING
        )
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signUp(email: String, password: String): Result<String> =
        signUpWithProfile(email, password, email.substringBefore("@"))

    override suspend fun signUpWithProfile(email: String, password: String, username: String): Result<String> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
        val user = result.user ?: error("Firebase did not return a user account")

        // Preserve the requested username in the Firebase Auth profile while keeping
        // database records locked until the email address has been verified.
        user.updateProfile(
            UserProfileChangeRequest.Builder().setDisplayName(username.trim()).build()
        ).await()
        user.sendEmailVerification().await()
        user.uid
    }

    override suspend fun ensureProfileExists(userId: String, email: String, username: String?): Result<Unit> = runCatching {
        val user = auth.currentUser
        require(user != null && user.uid == userId) {
            "The signed-in Firebase user does not match the requested profile"
        }
        user.reload().await()
        user.getIdToken(true).await()
        require(user.isEmailVerified) { "Verify your email address before creating your profile" }
        writeUserRecords(
            user,
            username?.trim()?.takeIf { it.isNotEmpty() } ?: email.substringBefore("@"))
    }

    private suspend fun writeUserRecords(
        user: FirebaseUser,
        username: String
    ) {
        require(user.isEmailVerified) { "Email verification is required before writing account data" }

        val normalized = username.trim().lowercase()
            .ifBlank { user.email?.substringBefore("@")?.lowercase() ?: "member" }
        val createdAt = user.metadata?.creationTimestamp ?: System.currentTimeMillis()

        // Keep the email address in the private users node only. Public profile reads
        // must never expose the account's private email address.
        val userData = hashMapOf<String, Any>(
            "uid" to user.uid,
            "id" to user.uid,
            "email" to (user.email ?: ""),
            "username" to normalized,
            "emailVerified" to true,
            "createdAt" to createdAt,
            "updatedAt" to ServerValue.TIMESTAMP
        )
        val profileData = hashMapOf<String, Any>(
            "uid" to user.uid,
            "id" to user.uid,
            "username" to normalized,
            "displayName" to normalized,
            "emailVerified" to true,
            "createdAt" to createdAt,
            "updatedAt" to ServerValue.TIMESTAMP
        )

        // Realtime Database creates these paths automatically when updateChildren()
        // writes them. Multi-location update keeps records and the username index in sync.
        val updates = hashMapOf<String, Any?>(
            "users/${user.uid}" to userData,
            "profiles/${user.uid}" to profileData,
            "usernames/$normalized" to user.uid
        )
        database.reference.updateChildren(updates).await()
    }

    override suspend fun signIn(email: String, password: String): Result<String> = runCatching {
        val user = auth.signInWithEmailAndPassword(email.trim(), password).await().user
            ?: error("Firebase did not return the signed-in user")
        user.reload().await()
        user.getIdToken(true).await()

        // Keep unverified accounts out of the app and out of protected database paths.
        // AuthViewModel detects this account and opens the verification screen.
        if (user.isEmailVerified) {
            writeUserRecords(
                user,
                user.displayName?.trim()?.takeIf { it.isNotEmpty() }
                    ?: user.email?.substringBefore("@")
                    ?: "member")
        }
        user.uid
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        AccountSyncManager.clearAllLocalData(
            cachedMessageDao = cachedMessageDao,
            cachedConversationDao = cachedConversationDao,
            postDao = postDao,
            storyDao = storyDao,
            offlineActionRepository = offlineActionRepository,
            syncManager = syncManager
        )
        auth.signOut()
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid
    override fun getCurrentUserEmail(): String? = auth.currentUser?.email
    override fun isEmailVerified(): Boolean = auth.currentUser?.isEmailVerified == true

    // A cached Firebase session must not bypass the email-verification gate on app restart.
    override fun restoreSession(): Boolean = auth.currentUser?.isEmailVerified == true

    override suspend fun refreshSession(): Result<Unit> = runCatching {
        val user = auth.currentUser ?: error("No Firebase user is signed in")
        user.reload().await()
        user.getIdToken(true).await()
        if (user.isEmailVerified) {
            writeUserRecords(
                user,
                user.displayName?.trim()?.takeIf { it.isNotEmpty() }
                    ?: user.email?.substringBefore("@")
                    ?: "member")
        }
        Unit
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email.trim()).await()
    }

    override suspend fun resendVerificationEmail(email: String): Result<Unit> = runCatching {
        val user = auth.currentUser ?: error("Register or sign in before requesting email verification")
        require(user.email.equals(email.trim(), ignoreCase = true)) {
            "The requested email does not match the current account"
        }
        user.reload().await()
        require(!user.isEmailVerified) { "This email address is already verified. You can sign in now." }
        user.sendEmailVerification().await()
    }

    override suspend fun updatePassword(password: String): Result<Unit> = runCatching {
        (auth.currentUser ?: error("No Firebase user is signed in")).updatePassword(password).await()
    }

    override suspend fun updatePhoneNumber(phone: String): Result<Unit> =
        unsupported("Phone number updates require a verified phone credential")

    override suspend fun getOAuthUrl(provider: String, redirectUrl: String): Result<String> =
        unsupported("OAuth provider '$provider' has not yet been migrated to Firebase")

    override suspend fun handleOAuthCallback(code: String?, accessToken: String?, refreshToken: String?): Result<Unit> =
        unsupported("Legacy OAuth callbacks are not supported by Firebase Authentication")

    override suspend fun signInWithOAuth(provider: SocialProvider, redirectUrl: String): Result<Unit> =
        unsupported("This OAuth provider has not yet been migrated to Firebase")

    override suspend fun signInWithGoogleIdToken(idToken: String): Result<String> = runCatching {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val user = auth.signInWithCredential(credential).await().user
            ?: error("Firebase did not return the Google user")
        user.reload().await()
        user.getIdToken(true).await()
        require(user.isEmailVerified) { "Verify your Google account email before continuing" }
        writeUserRecords(
            user,
            user.displayName ?: user.email?.substringBefore("@") ?: "member")
        user.uid
    }

    override suspend fun importSession(accessToken: String, refreshToken: String): Result<Unit> =
        unsupported("Supabase sessions cannot be imported into Firebase Authentication")

    override suspend fun linkIdentity(provider: SocialProvider): Result<Unit> =
        unsupported("Identity linking for this provider has not yet been migrated to Firebase")

    override suspend fun unlinkIdentity(identityId: String): Result<Unit> =
        unsupported("Identity unlinking has not yet been migrated to Firebase")

    override suspend fun getLinkedIdentities(): Result<List<String>> =
        Result.success(auth.currentUser?.providerData?.map { it.providerId } ?: emptyList())

    override suspend fun updateEmail(email: String): Result<Unit> = runCatching {
        (auth.currentUser ?: error("No Firebase user is signed in")).verifyBeforeUpdateEmail(email.trim()).await()
    }

    override suspend fun deleteAccount(): Result<Unit> = runCatching {
        val user = auth.currentUser ?: error("No Firebase user is signed in")
        val userSnapshot = database.reference.child("users").child(user.uid).get().await()
        val oldUsername = userSnapshot.child("username").getValue(String::class.java)
        val updates = hashMapOf<String, Any?>(
            "users/${user.uid}" to null,
            "profiles/${user.uid}" to null
        )
        if (!oldUsername.isNullOrBlank()) updates["usernames/${oldUsername.lowercase()}"] = null
        database.reference.updateChildren(updates).await()
        user.delete().await()
    }

    private fun <T> unsupported(message: String): Result<T> =
        Result.failure(UnsupportedOperationException(message))
}
