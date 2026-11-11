package com.mysketch.data.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.mysketch.shared.domain.model.User
import com.mysketch.shared.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

/**
 * Routes the pre-registration username availability check to Firestore.
 * Other user/profile operations remain delegated until their own migration.
 */
class FirebaseUsernameAvailabilityRepository(
    private val delegate: UserRepository,
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository by delegate {

    override suspend fun isUsernameAvailable(username: String): Result<Boolean> = runCatching {
        val normalized = username.trim().lowercase()
        require(normalized.isNotBlank()) { "Username cannot be blank" }
        val matches = firestore.collection("users")
            .whereEqualTo("username", normalized)
            .limit(1)
            .get()
            .await()
        matches.isEmpty
    }
}
