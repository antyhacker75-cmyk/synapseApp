package com.mysketch.shared.domain.usecase.follow

import com.mysketch.shared.domain.model.User
import com.mysketch.shared.domain.repository.FollowRepository

class GetFollowersUseCase(
    private val followRepository: FollowRepository
) {
    suspend operator fun invoke(userId: String): Result<List<User>> {
        return followRepository.getFollowers(userId)
    }
}
