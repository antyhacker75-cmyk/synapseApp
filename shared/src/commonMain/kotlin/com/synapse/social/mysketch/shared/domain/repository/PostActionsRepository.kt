package com.mysketch.shared.domain.repository

interface PostActionsRepository {
    suspend fun deletePost(postId: String): Result<Unit>
    suspend fun toggleComments(postId: String): Result<Unit>
    suspend fun updateLocalPost(post: Any): Result<Unit> { return Result.success(Unit) } // Default for now
}
