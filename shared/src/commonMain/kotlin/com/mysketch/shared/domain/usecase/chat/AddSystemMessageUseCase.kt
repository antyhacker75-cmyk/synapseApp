package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.Message
import com.mysketch.shared.domain.repository.ChatRepository

class AddSystemMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: String, content: String): Result<Message> {
        return repository.addSystemMessage(chatId, content)
    }
}
