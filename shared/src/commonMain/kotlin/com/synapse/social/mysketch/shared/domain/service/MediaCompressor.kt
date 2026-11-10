package com.mysketch.shared.domain.service

import com.mysketch.shared.domain.model.settings.MediaUploadQuality

interface MediaCompressor {
    suspend fun compress(filePath: String): Result<String>
    suspend fun compress(filePath: String, quality: MediaUploadQuality): Result<String>
}
