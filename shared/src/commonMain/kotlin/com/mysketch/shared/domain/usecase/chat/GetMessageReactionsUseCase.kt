package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.MessageReaction
import com.mysketch.shared.domain.repository.ChatRepository

class GetMessageReactionsUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messageId: String): Result<List<MessageReaction>> {
        return repository.getReactionsForMessage(messageId)
    }
}
