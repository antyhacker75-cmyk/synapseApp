package com.mysketch.shared.domain.repository.ai

interface AiRepository {
    suspend fun generateSmartReplies(recentMessages: List<String>, conversationId: String? = null): Result<List<String>>
}
