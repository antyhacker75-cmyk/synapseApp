package com.mysketch.domain.repository

interface ReportRepository {
    suspend fun createReport(postId: String, reason: String, description: String? = null): Result<Unit>
}
