package com.mysketch.shared.di

import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.data.datasource.ChatMessageDataSource
import com.mysketch.shared.data.local.InMemorySyncStateStorage
import com.mysketch.shared.data.local.SyncStateStorage
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.sync.MessageEntitySyncerFactory
import com.mysketch.shared.data.sync.NetworkMonitor
import com.mysketch.shared.data.sync.SyncManager
import org.koin.dsl.module

/**
 * Koin module for the synchronization infrastructure.
 * SyncManager is the central coordinator; entity syncers are registered on creation.
 */
val syncModule = module {

    single<SyncStateStorage> { InMemorySyncStateStorage() }

    single {
        SyncManager(
            supabaseClient = SupabaseClient.client,
            syncStateStorage = get(),
            offlineActionRepository = getOrNull()
        )
    }

    // Wire up the message syncer and register it with SyncManager
    single {
        val syncManager: SyncManager = get()
        val messageDataSource: ChatMessageDataSource = get()
        val cachedMessageDao: CachedMessageDao = get()

        val messageSyncer = MessageEntitySyncerFactory.create(messageDataSource, cachedMessageDao)
        syncManager.registerSyncer("message", messageSyncer)
        syncManager.subscribeRealtime("message", "messages")

        syncManager
    }

    single { NetworkMonitor(syncManager = get()) }
}
