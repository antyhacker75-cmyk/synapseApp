package com.mysketch.domain.usecase.search

import com.mysketch.domain.model.FeelingActivity
import javax.inject.Inject

class SearchFeelingsUseCase @Inject constructor() {
    operator fun invoke(query: String, allFeelings: List<FeelingActivity>): List<FeelingActivity> {
        return if (query.isBlank()) {
            allFeelings
        } else {
            allFeelings.filter { it.text.contains(query, ignoreCase = true) }
        }
    }
}
