package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class BulkDeleteMessagesForMeUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messageIds: List<String>): Result<Unit> = repository.deleteMessagesForMe(messageIds)
}
