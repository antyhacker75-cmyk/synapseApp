package com.mysketch.domain.usecase.ai

import com.mysketch.domain.model.Post
import com.mysketch.domain.repository.ai.AiRepository

class SummarizeThreadUseCase(private val aiRepository: AiRepository) {
    suspend operator fun invoke(posts: List<Post>, conversationId: String? = null): Result<String> {
        return aiRepository.summarizeThread(posts, conversationId)
    }
}
