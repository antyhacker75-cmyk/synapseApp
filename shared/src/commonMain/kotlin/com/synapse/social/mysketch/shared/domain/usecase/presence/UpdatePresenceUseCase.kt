package com.mysketch.shared.domain.usecase.presence

import com.mysketch.shared.domain.repository.PresenceRepository

class UpdatePresenceUseCase(private val repository: PresenceRepository) {
    suspend operator fun invoke(isOnline: Boolean, currentChatId: String? = null) = repository.updatePresence(isOnline, currentChatId)
}
