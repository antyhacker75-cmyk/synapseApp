package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.business.AnalyticsData
import com.mysketch.shared.domain.model.business.BusinessAccount
import com.mysketch.shared.domain.model.business.RevenueData

interface BusinessRepository {
    suspend fun getBusinessAccount(userId: String): Result<BusinessAccount?>
    suspend fun getAnalytics(userId: String): Result<AnalyticsData>
    suspend fun getRevenue(userId: String): Result<RevenueData>
    suspend fun createBusinessAccount(userId: String): Result<Unit>
    suspend fun updateMonetization(userId: String, enabled: Boolean): Result<Unit>
    suspend fun applyForVerification(userId: String): Result<Unit>
}
