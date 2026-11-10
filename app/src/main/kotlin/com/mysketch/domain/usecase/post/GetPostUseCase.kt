package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.PostRepository
import com.mysketch.domain.model.Post
import javax.inject.Inject

class GetPostUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: String): Result<Post?> {
        return postRepository.getPost(postId)
    }
}
