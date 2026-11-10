package com.mysketch.shared.domain.usecase.call

import com.mysketch.shared.domain.model.call.CallDirection
import com.mysketch.shared.domain.model.call.CallRequest
import com.mysketch.shared.domain.model.call.CallSession
import com.mysketch.shared.domain.repository.CallRepository

/**
 * Places a call.
 *
 * The two checks here are the ones a UI can get wrong by accident: dialling nobody, and dialling
 * without the conversation the call belongs to. The second is not bookkeeping — the backend needs the
 * chat id to prove both users are members of it, so a call without one cannot be authorised and must
 * not be attempted at all.
 *
 * Answering is deliberately not validated beyond this: the repository resolves the invitation from the
 * backend's own ringing row, so an incoming request that no server ever made finds nothing.
 */
class StartCallUseCase(
    private val callRepository: CallRepository
) {
    suspend operator fun invoke(request: CallRequest): Result<CallSession> {
        if (request.participant.userId.isBlank()) {
            return Result.failure(IllegalArgumentException("A call needs somebody to call"))
        }
        if (request.direction == CallDirection.OUTGOING && request.chatId.isNullOrBlank()) {
            return Result.failure(
                IllegalArgumentException("An outgoing call needs the conversation it came from")
            )
        }
        return callRepository.start(request)
    }
}
