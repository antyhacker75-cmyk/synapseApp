package com.mysketch.domain.model

data class FeedbackSubmission(
    val category: String,
    val email: String,
    val description: String,
    val appVersion: String,
    val buildNumber: String,
    val deviceInfo: String
)
