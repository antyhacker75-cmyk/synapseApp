package com.mysketch.domain.usecase.reaction

import com.mysketch.domain.model.Post
import com.mysketch.domain.repository.ReactionRepository
import javax.inject.Inject

class PopulatePostReactionsUseCase @Inject constructor(
    private val reactionRepository: ReactionRepository
) {
    suspend operator fun invoke(posts: List<Post>): List<Post> =
        reactionRepository.populatePostReactions(posts)
}
