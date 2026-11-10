package com.mysketch.domain.model.models

enum class UploadState {
    QUEUED,
    COMPRESSING,
    UPLOADING,
    COMPLETED,
    FAILED,
    CANCELLED
}
