package com.mysketch.feature.profile.editprofile.appearance

import android.net.Uri
import com.mysketch.domain.model.UserProfile
import com.mysketch.presentation.editprofile.UploadState

data class ProfileAppearanceUiState(
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val profile: UserProfile? = null,
    val avatarUrl: String? = null,
    val coverUrl: String? = null,
    val pendingCoverUri: Uri? = null,
    val uploadState: UploadState = UploadState.Idle,
    val error: String? = null,
    val hasPendingChanges: Boolean = false,
    val showRemoveCoverDialog: Boolean = false,
    val showUnsavedChangesDialog: Boolean = false
)
