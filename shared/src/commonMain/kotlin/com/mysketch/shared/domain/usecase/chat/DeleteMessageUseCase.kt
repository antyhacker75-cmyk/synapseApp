package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class DeleteMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messageId: String): Result<Unit> = repository.deleteMessage(messageId)
}
