package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository

class GetOAuthUrlUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(provider: String, redirectUrl: String): Result<String> {
        return repository.getOAuthUrl(provider, redirectUrl)
    }
}
