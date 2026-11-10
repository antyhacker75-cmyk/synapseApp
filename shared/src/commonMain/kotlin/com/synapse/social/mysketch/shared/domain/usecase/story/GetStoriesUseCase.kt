package com.mysketch.shared.domain.usecase.story

import com.mysketch.shared.domain.model.Story
import com.mysketch.shared.domain.repository.StoryRepository

class GetStoriesUseCase(private val repository: StoryRepository) {
    @Throws(Exception::class)
    suspend operator fun invoke(): List<Story> {
        return repository.getStories()
    }
}
