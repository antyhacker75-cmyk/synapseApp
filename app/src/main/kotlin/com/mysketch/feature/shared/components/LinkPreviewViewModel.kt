package com.mysketch.feature.shared.components

import androidx.lifecycle.ViewModel
import com.mysketch.shared.domain.usecase.GetLinkMetadataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LinkPreviewViewModel @Inject constructor(
    val getLinkMetadataUseCase: GetLinkMetadataUseCase
) : ViewModel()
