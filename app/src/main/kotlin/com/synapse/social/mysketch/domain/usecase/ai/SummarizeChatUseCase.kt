package com.mysketch.domain.usecase.ai

import com.mysketch.domain.repository.ai.AiRepository

class SummarizeChatUseCase(private val aiRepository: AiRepository) {
    suspend operator fun invoke(messages: List<String>, conversationId: String? = null): Result<String> {
        return aiRepository.summarizeChat(messages, conversationId)
    }
}
