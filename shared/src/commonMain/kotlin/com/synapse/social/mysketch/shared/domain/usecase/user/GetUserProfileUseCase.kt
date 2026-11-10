package com.mysketch.shared.domain.usecase.user

import com.mysketch.shared.domain.model.User
import com.mysketch.shared.domain.repository.UserRepository

class GetUserProfileUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(uid: String): Result<User?> {
        return userRepository.getUserProfile(uid)
    }
}
