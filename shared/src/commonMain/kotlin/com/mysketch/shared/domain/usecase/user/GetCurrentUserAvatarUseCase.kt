package com.mysketch.shared.domain.usecase.user

import com.mysketch.shared.domain.repository.UserRepository

class GetCurrentUserAvatarUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Result<String?> = repository.getCurrentUserAvatar()
}
