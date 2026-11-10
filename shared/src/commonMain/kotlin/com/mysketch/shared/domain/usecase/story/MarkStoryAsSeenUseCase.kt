package com.mysketch.shared.domain.usecase.story

import com.mysketch.shared.domain.repository.StoryRepository

class MarkStoryAsSeenUseCase(private val repository: StoryRepository) {
    @Throws(Exception::class)
    suspend operator fun invoke(storyId: String, viewerId: String) {
        repository.markAsSeen(storyId, viewerId)
    }
}
