package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.repository.UserRepository

class CheckUsernameAvailabilityUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String): Result<Boolean> {
        return userRepository.isUsernameAvailable(username)
    }
}
