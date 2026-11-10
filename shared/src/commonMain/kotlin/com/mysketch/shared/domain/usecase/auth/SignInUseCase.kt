package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.signIn(email, password)
    }
}
