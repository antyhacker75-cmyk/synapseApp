package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository

class GetCurrentUserEmailUseCase(private val repository: AuthRepository) {
    operator fun invoke(): String? {
        return repository.getCurrentUserEmail()
    }
}
