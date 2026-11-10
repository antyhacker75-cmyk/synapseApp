package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class EditMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messageId: String, newContent: String): Result<Unit> =
        repository.editMessage(messageId, newContent)
}
