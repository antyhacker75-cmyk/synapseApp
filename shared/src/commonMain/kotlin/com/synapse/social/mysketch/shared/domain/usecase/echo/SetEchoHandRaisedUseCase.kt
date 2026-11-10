package com.mysketch.shared.domain.usecase.echo

import com.mysketch.shared.domain.repository.EchoRoomRepository

class SetEchoHandRaisedUseCase(
    private val repository: EchoRoomRepository
) {
    suspend operator fun invoke(roomId: String, raised: Boolean): Result<Unit> =
        repository.setHandRaised(roomId, raised)
}
