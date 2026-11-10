package com.mysketch.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PollOption(
    val text: String,
    val votes: Int = 0
)
