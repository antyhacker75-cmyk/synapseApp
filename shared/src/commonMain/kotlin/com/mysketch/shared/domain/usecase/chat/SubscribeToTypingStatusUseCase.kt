package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.TypingStatus
import com.mysketch.shared.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SubscribeToTypingStatusUseCase(private val repository: ChatRepository) {
    operator fun invoke(chatId: String): Flow<TypingStatus> = repository.subscribeToTypingStatus(chatId)
}
