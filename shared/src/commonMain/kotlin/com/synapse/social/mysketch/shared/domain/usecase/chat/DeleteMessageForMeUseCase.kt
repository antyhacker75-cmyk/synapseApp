package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class DeleteMessageForMeUseCase (private val repository: ChatRepository) {
    suspend operator fun invoke(messageId: String): Result<Unit> = repository.deleteMessageForMe(messageId)
}
