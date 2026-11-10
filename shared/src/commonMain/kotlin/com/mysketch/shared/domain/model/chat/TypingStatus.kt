package com.mysketch.shared.domain.model.chat

data class TypingStatus(
    val userId: String,
    val chatId: String,
    val isTyping: Boolean
)
