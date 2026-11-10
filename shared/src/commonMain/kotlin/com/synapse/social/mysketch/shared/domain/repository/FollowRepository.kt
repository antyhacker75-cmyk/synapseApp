package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.User

interface FollowRepository {
    suspend fun getFollowers(userId: String): Result<List<User>>
    suspend fun getFollowing(userId: String): Result<List<User>>
}
