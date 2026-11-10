package com.mysketch.domain.usecase.profile

import com.mysketch.data.repository.ProfileActionRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArchiveProfileUseCase @Inject constructor(
    private val repository: ProfileActionRepositoryImpl
) {
    operator fun invoke(userId: String, isArchived: Boolean): Flow<Result<Unit>> = flow {
        emit(repository.archiveProfile(userId, isArchived))
    }
}
