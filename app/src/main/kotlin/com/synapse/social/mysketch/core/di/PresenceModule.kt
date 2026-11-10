package com.mysketch.core.di

import com.mysketch.shared.data.repository.SupabasePresenceRepository
import com.mysketch.shared.domain.repository.PresenceRepository
import com.mysketch.shared.domain.usecase.presence.ObserveUserActiveStatusUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserPresenceDetailUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserPresenceUseCase
import com.mysketch.shared.domain.usecase.presence.StartPresenceTrackingUseCase
import com.mysketch.shared.domain.usecase.presence.StopPresenceTrackingUseCase
import com.mysketch.shared.domain.usecase.presence.UpdatePresenceUseCase
import com.mysketch.shared.domain.usecase.presence.UpdateCurrentChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresenceModule {
    
    @Provides
    @Singleton
    fun provideUpdatePresenceUseCase(repository: PresenceRepository) = UpdatePresenceUseCase(repository)
    
    @Provides
    @Singleton
    fun provideStartPresenceTrackingUseCase(repository: PresenceRepository) = StartPresenceTrackingUseCase(repository)
    
    @Provides
    @Singleton
    fun provideStopPresenceTrackingUseCase(repository: PresenceRepository) = StopPresenceTrackingUseCase(repository)
    
    @Provides
    @Singleton
    fun provideObserveUserPresenceUseCase(repository: PresenceRepository) = ObserveUserPresenceUseCase(repository)
    
    @Provides
    @Singleton
    fun provideObserveUserActiveStatusUseCase(repository: PresenceRepository) = ObserveUserActiveStatusUseCase(repository)

    @Provides
    @Singleton
    fun provideObserveUserPresenceDetailUseCase(repository: PresenceRepository) = ObserveUserPresenceDetailUseCase(repository)
    
    @Provides
    @Singleton
    fun provideUpdateCurrentChatUseCase(repository: PresenceRepository) = UpdateCurrentChatUseCase(repository)
}

