package com.mysketch.shared.data.local.database

import com.mysketch.shared.domain.model.Story

interface StoryDao {
    suspend fun insert(story: Story)
    suspend fun insertAll(stories: List<Story>)
    suspend fun getAll(): List<Story>
    suspend fun deleteById(id: String)
    suspend fun deleteAll()
}
