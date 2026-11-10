package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.model.auth.SocialProvider

class SignInWithOAuthUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(provider: SocialProvider, redirectUrl: String): Result<Unit> {
        if (redirectUrl.isBlank()) {
            return Result.failure(IllegalArgumentException("Redirect URL cannot be empty"))
        }
        return repository.signInWithOAuth(provider, redirectUrl)
    }
}
