package com.mysketch.shared.data.datasource

import com.mysketch.shared.domain.model.User

interface UserDataSource {
    suspend fun isUsernameAvailable(username: String): Result<Boolean>
    suspend fun getUserProfile(uid: String): Result<User?>
    suspend fun searchUsers(query: String): Result<List<User>>
    suspend fun updateUserProfile(uid: String, updates: Map<String, Any?>): Result<User?>
    suspend fun getCurrentUserAvatar(): Result<String?>
}
