package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.Notification
import com.mysketch.shared.domain.model.NotificationPreferences
import com.mysketch.shared.domain.model.NotificationAnalytics
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun fetchNotifications(userId: String, limit: Long = 50, before: String? = null, beforeId: String? = null): List<Notification>
    fun getRealtimeNotifications(userId: String): Flow<Notification>
    suspend fun markAsRead(userId: String, notificationId: String)
    suspend fun fetchPreferences(userId: String): NotificationPreferences?
    suspend fun updatePreferences(userId: String, preferences: NotificationPreferences)
    suspend fun logAnalytics(analytics: NotificationAnalytics)
    suspend fun updateOneSignalPlayerId(userId: String, playerId: String)
}
