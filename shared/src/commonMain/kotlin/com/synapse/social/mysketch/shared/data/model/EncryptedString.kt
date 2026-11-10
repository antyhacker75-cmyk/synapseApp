package com.mysketch.shared.data.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class EncryptedString(val value: String)
