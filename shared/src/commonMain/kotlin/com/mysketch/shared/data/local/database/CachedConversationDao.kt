package com.mysketch.shared.data.local.database

import com.mysketch.shared.domain.model.chat.Conversation

interface CachedConversationDao {
    suspend fun upsertAll(conversations: List<Conversation>)
    suspend fun getAll(): List<Conversation>
    suspend fun deleteAll()
    suspend fun deleteByChatId(chatId: String)
}
