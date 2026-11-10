package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): String? {
        return chatRepository.getCurrentUserId()
    }
}
