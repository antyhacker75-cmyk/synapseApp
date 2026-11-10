package com.mysketch.shared.domain.usecase.post

import com.mysketch.shared.domain.usecase.auth.GetCurrentUserIdUseCase

class IsPostOwnerUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) {
    operator fun invoke(postAuthorUid: String): Boolean {
        return getCurrentUserIdUseCase() == postAuthorUid
    }
}
