package com.mysketch.domain.usecase.profile

import com.mysketch.data.repository.EditProfileRepositoryImpl
import com.mysketch.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterestsSkillsUseCase @Inject constructor(
    private val repository: EditProfileRepositoryImpl
) {
    suspend fun getCurrentUserId(): String? = repository.getCurrentUserId()

    operator fun invoke(userId: String): Flow<Result<UserProfile>> {
        require(userId.isNotBlank()) { "User ID cannot be blank" }
        return repository.getUserProfile(userId)
    }
}
