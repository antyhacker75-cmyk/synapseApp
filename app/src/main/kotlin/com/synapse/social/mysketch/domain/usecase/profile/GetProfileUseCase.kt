package com.mysketch.domain.usecase.profile

import com.mysketch.domain.model.UserProfile
import com.mysketch.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(userId: String, refresh: Boolean = false): Flow<Result<UserProfile>> {
        require(userId.isNotBlank()) { "User ID cannot be blank" }
        return repository.getProfile(userId, refresh)
    }
}
