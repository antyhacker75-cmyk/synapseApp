package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.PostRepository
import javax.inject.Inject

class RepostPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String): Result<Unit> {
        return postRepository.resharePost(postId)
    }
}
