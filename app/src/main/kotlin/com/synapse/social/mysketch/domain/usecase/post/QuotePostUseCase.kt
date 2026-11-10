package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.PostRepository
import com.mysketch.domain.model.Post
import javax.inject.Inject

class QuotePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String, text: String): Result<Post> {
        return postRepository.quotePost(postId, text)
    }
}
