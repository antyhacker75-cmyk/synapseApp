package com.mysketch.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LinkedAccount(
    val platform: String,
    val username: String
)
