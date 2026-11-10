package com.mysketch.feature.profile.editprofile.interestsskills

data class InterestsSkillsUiState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val interests: List<String> = emptyList(),
    val pendingInterestInput: String = "",
    val inputError: String? = null,
    val hasChanges: Boolean = false
)
