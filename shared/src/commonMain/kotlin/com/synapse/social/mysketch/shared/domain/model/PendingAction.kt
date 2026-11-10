package com.mysketch.shared.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import com.mysketch.shared.util.TimeProvider

@Serializable
data class PendingAction(
    val id: String,
    val actionType: ActionType,
    val targetId: String,
    val payload: String? = null,
    val createdAt: Long = TimeProvider.nowMillis(),
    val retryCount: Int = 0,
    val lastAttemptAt: Long? = null
) {
    @Serializable
    enum class ActionType {
        EDIT,
        DELETE,
        FORWARD,
        LIKE,
        BOOKMARK,
        POLL_VOTE,
        SEND_MESSAGE
    }
}
