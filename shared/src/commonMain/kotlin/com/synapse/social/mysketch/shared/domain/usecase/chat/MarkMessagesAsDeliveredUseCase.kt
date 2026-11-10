package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class MarkMessagesAsDeliveredUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Result<Unit> {
        return repository.markMessagesAsDelivered(chatId)
    }
}
