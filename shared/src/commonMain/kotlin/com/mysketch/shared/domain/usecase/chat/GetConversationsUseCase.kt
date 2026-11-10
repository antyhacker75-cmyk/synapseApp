package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.Conversation
import com.mysketch.shared.domain.repository.ChatRepository

class GetConversationsUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(forceNetwork: Boolean = false): Result<List<Conversation>> = repository.getConversations(forceNetwork)
}
