package com.mysketch.shared.domain.usecase.notification

import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.repository.NotificationRepository
import com.mysketch.shared.domain.model.Notification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import io.github.aakira.napier.Napier

class SubscribeToNotificationsUseCase(
    private val notificationRepository: NotificationRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Notification> {
        val userId = authRepository.getCurrentUserId()
        if (userId == null) {
            Napier.w("Cannot subscribe to notifications: User not logged in")
            return emptyFlow()
        }

        return notificationRepository.getRealtimeNotifications(userId)
    }
}
