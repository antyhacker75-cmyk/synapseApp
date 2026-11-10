package com.mysketch.domain.repository

import com.mysketch.domain.model.FeedbackSubmission

interface FeedbackRepository {
    suspend fun submitFeedback(submission: FeedbackSubmission): Result<Unit>
}
