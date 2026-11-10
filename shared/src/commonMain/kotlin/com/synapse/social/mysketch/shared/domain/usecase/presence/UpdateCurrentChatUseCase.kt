package com.mysketch.shared.domain.usecase.presence

import com.mysketch.shared.domain.repository.PresenceRepository

class UpdateCurrentChatUseCase(
    private val presenceRepository: PresenceRepository
) {
    suspend operator fun invoke(chatId: String?) = presenceRepository.updatePresence(
        isOnline = true,
        currentChatId = chatId
    )
}
