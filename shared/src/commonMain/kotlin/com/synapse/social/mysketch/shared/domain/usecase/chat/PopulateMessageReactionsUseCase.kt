package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.Message
import com.mysketch.shared.domain.repository.ChatRepository

class PopulateMessageReactionsUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messages: List<Message>): List<Message> {
        return repository.getReactionsForMessages(messages)
    }
}
