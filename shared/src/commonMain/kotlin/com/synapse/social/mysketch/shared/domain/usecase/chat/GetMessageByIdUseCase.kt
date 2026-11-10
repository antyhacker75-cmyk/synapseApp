package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.Message
import com.mysketch.shared.domain.repository.ChatRepository

class GetMessageByIdUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messageId: String): Result<Message?> = repository.getMessageById(messageId)
}
