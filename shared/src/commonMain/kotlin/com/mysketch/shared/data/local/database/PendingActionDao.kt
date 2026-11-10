package com.mysketch.shared.data.local.database

import com.mysketch.shared.domain.model.PendingAction
import kotlinx.coroutines.flow.Flow

interface PendingActionDao {
    suspend fun insert(action: PendingAction)
    suspend fun delete(id: String)
    suspend fun getAll(): List<PendingAction>
    suspend fun update(id: String, retryCount: Int, lastAttemptAt: Long?)
    fun getAllAsFlow(): Flow<List<PendingAction>>
}
