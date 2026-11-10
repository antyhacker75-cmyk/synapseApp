package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.model.chat.DisappearingMode
import com.mysketch.shared.domain.repository.ChatRepository

class GetDisappearingModeUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(chatId: String): Result<DisappearingMode> {
        return repository.getDisappearingMode(chatId)
    }
}
