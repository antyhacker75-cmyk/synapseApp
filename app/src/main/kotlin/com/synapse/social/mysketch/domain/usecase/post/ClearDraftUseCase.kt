package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.DraftRepository
import javax.inject.Inject

class ClearDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    operator fun invoke() = repository.clearDraft()
}
