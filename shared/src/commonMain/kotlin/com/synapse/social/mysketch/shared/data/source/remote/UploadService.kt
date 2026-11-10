package com.mysketch.shared.data.source.remote

import com.mysketch.shared.domain.model.StorageConfig
import com.mysketch.shared.domain.model.UploadResult
import io.ktor.utils.io.ByteReadChannel

interface UploadService {
    suspend fun upload(
        fileProvider: suspend (Long) -> ByteReadChannel,
        fileSize: Long,
        fileName: String,
        config: StorageConfig,
        bucketName: String? = null,
        onProgress: (Float) -> Unit
    ): UploadResult
}
