package com.mysketch.data.repository

import com.mysketch.data.model.UserProfile
import com.mysketch.feature.profile.profile.components.MediaItem
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(userId: String, refresh: Boolean = false): Flow<Result<UserProfile>>
    suspend fun updateProfile(userId: String, profile: UserProfile): Result<UserProfile>
    suspend fun followUser(userId: String, targetUserId: String): Result<Unit>
    suspend fun unfollowUser(userId: String, targetUserId: String): Result<Unit>
    suspend fun getFollowers(userId: String, limit: Int = 20, offset: Int = 0): Result<List<UserProfile>>
    suspend fun getFollowing(userId: String, limit: Int = 20, offset: Int = 0): Result<List<UserProfile>>
    suspend fun getProfilePosts(userId: String, limit: Int = 10, offset: Int = 0): Result<List<com.mysketch.domain.model.Post>>
    suspend fun getProfilePhotos(userId: String, limit: Int = 20, offset: Int = 0): Result<List<MediaItem>>
    suspend fun getProfileReels(userId: String, limit: Int = 20, offset: Int = 0): Result<List<MediaItem>>
    suspend fun getProfileReplies(userId: String, limit: Int = 10, offset: Int = 0): Result<List<com.mysketch.domain.model.CommentWithUser>>
    suspend fun isFollowing(userId: String, targetUserId: String): Result<Boolean>
}
