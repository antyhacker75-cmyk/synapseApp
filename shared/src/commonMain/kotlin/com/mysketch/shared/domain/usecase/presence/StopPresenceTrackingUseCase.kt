package com.mysketch.shared.domain.usecase.presence

import com.mysketch.shared.domain.repository.PresenceRepository
import io.github.aakira.napier.Napier

class StopPresenceTrackingUseCase(private val repository: PresenceRepository) {
    suspend operator fun invoke() {
        Napier.d("StopPresenceTrackingUseCase invoked")
        repository.stopPresenceTracking()
    }
}
