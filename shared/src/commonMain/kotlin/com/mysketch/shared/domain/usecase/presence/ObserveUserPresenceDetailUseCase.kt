package com.mysketch.shared.domain.usecase.presence

import com.mysketch.shared.domain.model.presence.UserPresence
import com.mysketch.shared.domain.repository.PresenceRepository
import kotlinx.coroutines.flow.Flow

class ObserveUserPresenceDetailUseCase(
    private val repository: PresenceRepository
) {
    operator fun invoke(userId: String): Flow<UserPresence> = repository.observeUserPresenceDetail(userId)
}
