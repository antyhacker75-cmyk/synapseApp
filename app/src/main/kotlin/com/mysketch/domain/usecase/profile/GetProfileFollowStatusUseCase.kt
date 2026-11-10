package com.mysketch.domain.usecase.profile

import com.mysketch.domain.repository.ProfilePrivacyRepository
import com.mysketch.domain.model.ProfileFollowStatus
import javax.inject.Inject

class GetProfileFollowStatusUseCase @Inject constructor(
    private val repository: ProfilePrivacyRepository
) {
    suspend operator fun invoke(targetUid: String): Result<ProfileFollowStatus> =
        repository.getFollowStatus(targetUid)
}
