package com.mysketch.shared.domain.usecase.echo

import com.mysketch.shared.domain.model.echo.EchoRoomStatus
import com.mysketch.shared.domain.repository.EchoRoomRepository

/**
 * Keeps this member's `last_seen_at` fresh and reports whether the room still exists.
 *
 * An interruption is not a leave: the caller decides whether a failure is worth surfacing, which
 * is why this returns the failure instead of interpreting it.
 */
class EchoHeartbeatUseCase(
    private val repository: EchoRoomRepository
) {
    suspend operator fun invoke(roomId: String): Result<EchoRoomStatus> = repository.heartbeat(roomId)
}
