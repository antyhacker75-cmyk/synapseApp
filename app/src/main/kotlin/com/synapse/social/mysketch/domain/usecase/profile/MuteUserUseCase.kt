package com.mysketch.domain.usecase.profile

import com.mysketch.data.repository.ProfileActionRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MuteUserUseCase @Inject constructor(
    private val repository: ProfileActionRepositoryImpl
) {
    operator fun invoke(userId: String, mutedUserId: String): Flow<Result<Unit>> = flow {
        emit(repository.muteUser(userId, mutedUserId))
    }
}
