package com.mysketch.domain.usecase.profile

import com.mysketch.domain.repository.ProfilePrivacyRepository
import javax.inject.Inject

class RespondToFollowRequestUseCase @Inject constructor(
    private val repository: ProfilePrivacyRepository
) {
    suspend operator fun invoke(requesterUid: String, accept: Boolean): Result<Unit> =
        repository.respondToFollowRequest(requesterUid, accept)
}
