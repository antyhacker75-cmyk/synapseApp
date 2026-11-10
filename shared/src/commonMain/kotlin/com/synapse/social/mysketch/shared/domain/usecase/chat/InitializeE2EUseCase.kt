package com.mysketch.shared.domain.usecase.chat

import com.mysketch.shared.domain.repository.ChatRepository

class InitializeE2EUseCase(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.initializeE2EE()
    }
}
