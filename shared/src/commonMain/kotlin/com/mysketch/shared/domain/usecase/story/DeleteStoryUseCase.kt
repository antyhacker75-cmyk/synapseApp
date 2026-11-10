package com.mysketch.shared.domain.usecase.story

import com.mysketch.shared.domain.repository.StoryRepository

class DeleteStoryUseCase(private val repository: StoryRepository) {
    @Throws(Exception::class)
    suspend operator fun invoke(storyId: String) {
        repository.deleteStory(storyId)
    }
}
