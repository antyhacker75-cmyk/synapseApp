package com.mysketch.shared.domain.model.business

data class RevenueData(
    val totalEarnings: Double,
    val pendingPayout: Double,
    val lastPayoutDate: Long?
)
