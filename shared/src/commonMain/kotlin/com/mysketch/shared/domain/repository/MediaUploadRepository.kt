package com.mysketch.shared.domain.repository

import com.mysketch.shared.domain.model.StorageConfig
import com.mysketch.shared.domain.model.StorageProvider
import com.mysketch.shared.domain.model.UploadResult

interface MediaUploadRepository {
    suspend fun upload(
        filePath: String,
        provider: StorageProvider,
        config: StorageConfig,
        bucketName: String?,
        onProgress: (Float) -> Unit
    ): Result<UploadResult>

    fun deleteFile(filePath: String)

    suspend fun deleteImgBbImages(deleteUrls: List<String>)
}
