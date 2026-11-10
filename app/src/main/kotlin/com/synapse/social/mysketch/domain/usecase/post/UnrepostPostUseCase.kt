package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.PostRepository
import javax.inject.Inject

class UnrepostPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String): Result<Unit> {
        return postRepository.unresharePost(postId)
    }
}
