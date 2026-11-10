package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class BroadcastTypingStatusUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: String, isTyping: Boolean): Result<Unit> =
        repository.broadcastTypingStatus(chatId, isTyping)
}
