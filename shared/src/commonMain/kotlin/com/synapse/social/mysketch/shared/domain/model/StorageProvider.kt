
package com.mysketch.shared.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class StorageProvider {
    DEFAULT,
    IMGBB,
    CLOUDINARY,
    @SerialName("SUPABASE")
    PRIMARY,
    CLOUDFLARE_R2
}
