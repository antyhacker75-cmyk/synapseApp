package com.mysketch.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UploadResult(
    val url: String,
    val deleteUrl: String? = null
)
