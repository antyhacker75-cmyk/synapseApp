package com.mysketch.domain.repository

interface DraftRepository {
    fun getDraftText(): String?
    fun saveDraftText(text: String)
    fun clearDraft()
}
