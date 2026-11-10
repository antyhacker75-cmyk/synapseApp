package com.mysketch.feature.call

import com.mysketch.shared.domain.model.call.CallMediaType
import com.mysketch.shared.domain.model.call.CallSession
import com.mysketch.ui.navigation.AppDestination

data class CallLaunchRequest(
    val participantId: String,
    val participantName: String,
    val participantAvatarUrl: String? = null,
    val chatId: String? = null,
    val isVideo: Boolean = false,
    val isIncoming: Boolean = false
) {
    fun toDestination(): AppDestination.Call = AppDestination.Call(
        peerUserId = participantId,
        peerName = participantName,
        peerAvatarUrl = participantAvatarUrl,
        chatId = chatId,
        isVideo = isVideo,
        isIncoming = isIncoming
    )
}

/**
 * Dials the same person again, in the same conversation and media type.
 *
 * Always outgoing: this describes a call the user is placing now, whatever direction the session
 * it was read from had. `chat_id` is NOT NULL on `call_sessions`, so a history row always carries
 * the conversation an outgoing call needs.
 */
fun CallSession.toCallAgainRequest(): CallLaunchRequest = CallLaunchRequest(
    participantId = participant.userId,
    participantName = participant.displayName,
    participantAvatarUrl = participant.avatarUrl,
    chatId = chatId,
    isVideo = mediaType == CallMediaType.VIDEO,
    isIncoming = false
)

/** Opens the ringing screen for a call that is already addressed to this user. */
fun CallSession.toIncomingLaunchRequest(): CallLaunchRequest = CallLaunchRequest(
    participantId = participant.userId,
    participantName = participant.displayName,
    participantAvatarUrl = participant.avatarUrl,
    chatId = chatId,
    isVideo = mediaType == CallMediaType.VIDEO,
    isIncoming = true
)
