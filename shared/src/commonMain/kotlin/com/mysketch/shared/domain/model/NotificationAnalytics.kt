package com.mysketch.shared.domain.model

data class NotificationAnalytics(
    val id: String?,
    val notificationId: String,
    val userId: String,
    val deliveredAt: String?,
    val openedAt: String?,
    val interactionType: String,
    val platform: String,
    val appVersion: String?
)
