package com.mysketch.shared.domain.model.presence

data class UserPresence(
    val userId: String,
    val isOnline: Boolean,
    val lastSeen: String? = null,
    val status: String? = null
)
