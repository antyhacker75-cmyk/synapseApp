package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.MessageReaction
import com.mysketch.shared.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SubscribeToMessageReactionsUseCase(private val repository: ChatRepository) {
    operator fun invoke(chatId: String): Flow<MessageReaction> {
        return repository.subscribeToMessageReactions(chatId)
    }
}
