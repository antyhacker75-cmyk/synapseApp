package com.mysketch.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class MediaType {
    IMAGE,
    PHOTO,
    VIDEO,
    AUDIO,
    OTHER
}
