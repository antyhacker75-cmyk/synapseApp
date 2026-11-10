package com.mysketch.shared.domain.usecase.echo

import com.mysketch.shared.domain.model.echo.EchoRoomDetail
import com.mysketch.shared.domain.repository.EchoRoomRepository
import kotlinx.coroutines.flow.Flow

class ObserveEchoRoomUseCase(
    private val repository: EchoRoomRepository
) {
    operator fun invoke(roomId: String): Flow<EchoRoomDetail> = repository.observeRoom(roomId)
}
