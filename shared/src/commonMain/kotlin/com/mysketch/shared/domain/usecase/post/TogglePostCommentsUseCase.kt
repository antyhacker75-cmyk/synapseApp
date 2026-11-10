package com.mysketch.shared.domain.usecase.post

import com.mysketch.shared.domain.repository.PostActionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TogglePostCommentsUseCase(
    private val repository: PostActionsRepository
) {
    operator fun invoke(postId: String): Flow<Result<Unit>> = flow {
        emit(repository.toggleComments(postId))
    }
}
