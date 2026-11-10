package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository

class GetCurrentUserIdUseCase(private val repository: AuthRepository) {
    operator fun invoke(): String? {
        return repository.getCurrentUserId()
    }
}
