package com.mysketch.domain.repository

import com.mysketch.domain.model.CreatePostRequest

interface ReelSubmissionHandler {
    suspend fun submitReel(request: CreatePostRequest, onProgress: (Float) -> Unit): Result<Unit>
}
