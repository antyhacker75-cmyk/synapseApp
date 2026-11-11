package com.mysketch.data.auth

import com.google.firebase.database.FirebaseDatabase
import com.mysketch.shared.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

/**
 * Checks the dedicated Realtime Database username index.
 * The index stores only normalized username -> Firebase UID mappings.
 */
class FirebaseUsernameAvailabilityRepository(
    private val delegate: UserRepository,
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
) : UserRepository by delegate {

    override suspend fun isUsernameAvailable(username: String): Result<Boolean> = runCatching {
        val normalized = username.trim().lowercase()
        require(normalized.isNotBlank()) { "Username cannot be blank" }
        val snapshot = database.reference
            .child("usernames")
            .child(normalized)
            .get()
            .await()
        !snapshot.exists()
    }
}
