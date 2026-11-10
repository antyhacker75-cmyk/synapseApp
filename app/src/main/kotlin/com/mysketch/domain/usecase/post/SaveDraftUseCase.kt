package com.mysketch.domain.usecase.post

import com.mysketch.domain.repository.DraftRepository
import javax.inject.Inject

class SaveDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    operator fun invoke(text: String) = repository.saveDraftText(text)
}
