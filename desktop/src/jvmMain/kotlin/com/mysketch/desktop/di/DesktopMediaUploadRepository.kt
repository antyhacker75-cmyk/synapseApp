package com.mysketch.desktop.di

import com.mysketch.shared.domain.model.StorageConfig
import com.mysketch.shared.domain.model.StorageProvider
import com.mysketch.shared.domain.model.UploadResult
import com.mysketch.shared.domain.repository.MediaUploadRepository

/**
 * No-op stub for [MediaUploadRepository] on desktop.
 * Media upload is not supported on the Compose Desktop target.
 */
class DesktopMediaUploadRepository : MediaUploadRepository {
    override suspend fun upload(
        filePath: String,
        provider: StorageProvider,
        config: StorageConfig,
        bucketName: String?,
        onProgress: (Float) -> Unit
    ): Result<UploadResult> = Result.failure(UnsupportedOperationException("Media upload is not supported on Desktop."))

    override fun deleteFile(filePath: String) {
        // No-op on desktop
    }

    override suspend fun deleteImgBbImages(deleteUrls: List<String>) {
        // No-op on desktop
    }
}
