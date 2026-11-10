package com.mysketch.domain.usecase.profile

import com.mysketch.domain.repository.ProfilePrivacyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LockProfileUseCase @Inject constructor(
    private val repository: ProfilePrivacyRepository
) {
    operator fun invoke(userId: String, isLocked: Boolean): Flow<Result<Unit>> = flow {
        emit(repository.lockProfile(userId, isLocked))
    }
}
