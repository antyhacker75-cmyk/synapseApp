package com.mysketch.domain.usecase.ai

import com.mysketch.domain.repository.ai.AiRepository

class SummarizeMessageUseCase(private val aiRepository: AiRepository) {
    suspend operator fun invoke(content: String, conversationId: String? = null): Result<String> =
        aiRepository.summarizeMessage(content, conversationId)
}
