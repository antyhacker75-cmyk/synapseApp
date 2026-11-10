package com.mysketch.di

import com.mysketch.shared.data.repository.SupabaseGroupCreationRepository
import com.mysketch.shared.data.crypto.SignalProtocolManager
import com.mysketch.shared.domain.repository.GroupCreationRepository
import com.mysketch.shared.data.datasource.SupabaseChatDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GroupCreationModule {
    @Provides
    @Singleton
    fun provideSupabaseChatDataSource(): SupabaseChatDataSource = SupabaseChatDataSource()

    @Provides
    @Singleton
    fun provideGroupCreationRepository(dataSource: SupabaseChatDataSource, signalProtocolManager: SignalProtocolManager): GroupCreationRepository =
        SupabaseGroupCreationRepository(dataSource, signalProtocolManager)
}
