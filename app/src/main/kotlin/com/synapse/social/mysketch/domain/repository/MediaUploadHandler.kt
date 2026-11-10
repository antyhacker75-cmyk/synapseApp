package com.mysketch.domain.repository

import com.mysketch.domain.model.MediaItem

interface MediaUploadHandler {
    suspend fun uploadMedia(items: List<MediaItem>, onProgress: (Float) -> Unit): List<MediaItem>
}
