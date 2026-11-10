package com.mysketch.domain.usecase.profile

import com.mysketch.domain.repository.ProfilePrivacyRepository
import com.mysketch.domain.model.ProfileFollowRequest
import javax.inject.Inject

class GetPendingFollowRequestsUseCase @Inject constructor(
    private val repository: ProfilePrivacyRepository
) {
    suspend operator fun invoke(): Result<List<ProfileFollowRequest>> =
        repository.getPendingFollowRequests()
}
