package com.mysketch.domain.usecase.profile

import com.mysketch.domain.model.UserProfile
import com.mysketch.domain.repository.ProfileRepository
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String, limit: Int = 20, offset: Int = 0): Result<List<UserProfile>> {
        return repository.getFollowing(userId, limit, offset)
    }
}
