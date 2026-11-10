package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String, username: String): Result<String> {
        return repository.signUpWithProfile(email, password, username)
    }
}
