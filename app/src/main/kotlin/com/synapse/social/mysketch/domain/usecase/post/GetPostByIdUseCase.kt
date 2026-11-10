package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.PostRepository
import com.mysketch.domain.model.Post
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(postId: String): Result<Post?> {
        return repository.getPost(postId)
    }
}
