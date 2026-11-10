package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

/** Loads the current user's persisted notification mute setting for a chat. */
class GetConversationMuteStatusUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: String): Result<Boolean> {
        if (chatId.isBlank()) return Result.failure(IllegalArgumentException("Chat ID cannot be blank"))
        return repository.getConversationMuteStatus(chatId)
    }
}
