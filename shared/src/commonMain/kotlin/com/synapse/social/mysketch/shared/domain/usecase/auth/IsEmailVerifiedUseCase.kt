package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository

class IsEmailVerifiedUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean {
        return repository.isEmailVerified()
    }
}
