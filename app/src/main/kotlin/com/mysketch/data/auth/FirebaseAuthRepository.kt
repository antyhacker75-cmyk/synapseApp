package com.mysketch.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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
 * Android authentication implementation.
 *
 * Firebase Authentication owns credentials and sessions. Cloud Firestore owns
 * the user/profile documents. Supabase is deliberately not referenced here.
 */
class FirebaseAuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val cachedMessageDao: CachedMessageDao? = null,
    private val cachedConversationDao: CachedConversationDao? = null,
    private val postDao: PostDao? = null,
    private val storyDao: StoryDao? = null,
    private val offlineActionRepository: OfflineActionRepository? = null,
    private val syncManager: SyncManager? = null
) : AuthRepository {

    override val sessionStatus: Flow<AuthSessionStatus> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(if (firebaseAuth.currentUser != null) AuthSessionStatus.AUTHENTICATED
            else AuthSessionStatus.NOT_AUTHENTICATED)
        }
        trySend(if (auth.currentUser != null) AuthSessionStatus.AUTHENTICATED
        else AuthSessionStatus.INITIALIZING)
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signUp(email: String, password: String): Result<String> =
        signUpWithProfile(email, password, email.substringBefore("@"))

    override suspend fun signUpWithProfile(email: String, password: String, username: String): Result<String> = runCatching {
        val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
        val user = result.user ?: error("Firebase did not return a user account")
        // Do not delete a successfully-created Firebase Auth account when Firestore is
        // temporarily unavailable. The account must remain recoverable through sign-in
        // and password reset while profile creation is retried.
        writeUserDocuments(user, username, includeCreatedAt = true)
        runCatching { user.sendEmailVerification().await() }
        user.uid
    }

    override suspend fun ensureProfileExists(userId: String, email: String, username: String?): Result<Unit> = runCatching {
        val user = auth.currentUser
        require(user != null && user.uid == userId) {
            "The signed-in Firebase user does not match the requested profile"
        }
        writeUserDocuments(
            user,
            username?.trim()?.takeIf { it.isNotEmpty() } ?: email.substringBefore("@"),
            includeCreatedAt = false
        )
    }

    private suspend fun writeUserDocuments(
        user: FirebaseUser,
        username: String,
        includeCreatedAt: Boolean
    ) {
        val normalized = username.trim().lowercase().ifBlank { user.email?.substringBefore("@")?.lowercase() ?: "member" }
        val common = hashMapOf<String, Any>(
            "uid" to user.uid,
            "id" to user.uid,
            "email" to (user.email ?: ""),
            "username" to normalized,
            "emailVerified" to user.isEmailVerified,
            "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )
        // Avoid a read-before-write: the previous get().await() failed while Firestore
        // was offline and caused registration to roll back the Auth account.
        val userData = HashMap(common).apply {
            if (includeCreatedAt) {
                put("createdAt", com.google.firebase.firestore.FieldValue.serverTimestamp())
            }
        }
        val profileData = HashMap(common).apply { put("displayName", normalized) }
        firestore.collection("users").document(user.uid).set(userData, SetOptions.merge()).await()
        firestore.collection("profiles").document(user.uid).set(profileData, SetOptions.merge()).await()
    }

    override suspend fun signIn(email: String, password: String): Result<String> = runCatching {
        auth.signInWithEmailAndPassword(email.trim(), password).await().user?.uid
            ?: error("Firebase did not return the signed-in user")
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
    override fun restoreSession(): Boolean = auth.currentUser != null

    override suspend fun refreshSession(): Result<Unit> = runCatching {
        (auth.currentUser ?: error("No Firebase user is signed in")).getIdToken(true).await()
        Unit
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> = runCatching {
        auth.sendPasswordResetEmail(email.trim()).await()
    }

    override suspend fun resendVerificationEmail(email: String): Result<Unit> = runCatching {
        val user = auth.currentUser ?: error("Sign in before requesting email verification")
        require(user.email.equals(email.trim(), ignoreCase = true)) {
            "The requested email does not match the signed-in account"
        }
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
        writeUserDocuments(
            user,
            user.displayName ?: user.email?.substringBefore("@") ?: "member",
            includeCreatedAt = false
        )
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
        firestore.collection("users").document(user.uid).delete().await()
        firestore.collection("profiles").document(user.uid).delete().await()
        user.delete().await()
    }

    private fun <T> unsupported(message: String): Result<T> =
        Result.failure(UnsupportedOperationException(message))
}
