package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class MarkMessagesAsReadUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: String, shareReceipt: Boolean = true): Result<Unit> =
        repository.markMessagesAsRead(chatId, shareReceipt)
}
