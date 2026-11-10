package com.mysketch.shared.domain.usecase.presence

import com.mysketch.shared.domain.repository.PresenceRepository

class StartPresenceTrackingUseCase(private val repository: PresenceRepository) {
    suspend operator fun invoke() = repository.startPresenceTracking()
}
