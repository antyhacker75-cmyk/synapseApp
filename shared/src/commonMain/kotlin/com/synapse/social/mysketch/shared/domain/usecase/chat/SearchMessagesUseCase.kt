package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.Message
import com.mysketch.shared.domain.repository.ChatRepository

class SearchMessagesUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, query: String): Result<List<Message>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return chatRepository.searchMessages(chatId, query)
    }
}
