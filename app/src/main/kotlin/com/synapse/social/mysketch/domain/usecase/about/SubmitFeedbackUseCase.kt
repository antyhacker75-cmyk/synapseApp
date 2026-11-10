package com.mysketch.domain.usecase.about

import com.mysketch.domain.model.FeedbackSubmission
import com.mysketch.domain.repository.FeedbackRepository
import javax.inject.Inject

class SubmitFeedbackUseCase @Inject constructor(
    private val repository: FeedbackRepository
) {
    suspend operator fun invoke(submission: FeedbackSubmission): Result<Unit> =
        repository.submitFeedback(submission)
}
