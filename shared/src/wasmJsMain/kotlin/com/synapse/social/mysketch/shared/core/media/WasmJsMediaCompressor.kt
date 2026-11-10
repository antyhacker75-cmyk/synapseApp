package com.mysketch.shared.core.media

import com.mysketch.shared.domain.service.MediaCompressor
import com.mysketch.shared.domain.model.settings.MediaUploadQuality


class WasmJsMediaCompressor : MediaCompressor {
    override suspend fun compress(filePath: String): Result<String> = Result.success(filePath)

    override suspend fun compress(filePath: String, quality: MediaUploadQuality): Result<String> {
        return compress(filePath)
    }
}