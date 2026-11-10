package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.LinkPreview

interface LinkPreviewRepository {
    suspend fun getLinkPreview(url: String): LinkPreview?
}
