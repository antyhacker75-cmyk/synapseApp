package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.UserPreferences

interface UserPreferencesRepository {
    suspend fun getSecurityNotificationsEnabled(userId: String): Result<Boolean>
    suspend fun setSecurityNotificationsEnabled(userId: String, enabled: Boolean): Result<Unit>
    suspend fun getPreferences(userId: String): Result<UserPreferences>
    suspend fun updatePreferences(userId: String, update: (UserPreferences) -> UserPreferences): Result<Unit>
}
