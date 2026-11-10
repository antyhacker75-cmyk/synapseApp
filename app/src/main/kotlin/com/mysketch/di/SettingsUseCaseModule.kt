package com.mysketch.di

import com.mysketch.shared.domain.repository.AppRepository
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.repository.LinkPreviewRepository
import com.mysketch.shared.domain.repository.PlatformInfoProvider
import com.mysketch.shared.domain.repository.SettingsRepository
import com.mysketch.shared.domain.repository.UserPreferencesRepository
import com.mysketch.shared.domain.usecase.CheckForUpdatesUseCase
import com.mysketch.shared.domain.usecase.GetLinkMetadataUseCase
import com.mysketch.shared.domain.usecase.settings.GetContextualHeroCardsUseCase
import com.mysketch.shared.domain.usecase.settings.ObserveAccessibilitySettingsUseCase
import com.mysketch.shared.domain.usecase.settings.ObserveChatSettingsUseCase
import com.mysketch.shared.domain.usecase.settings.SearchSettingsUseCase
import com.mysketch.shared.domain.usecase.settings.SyncAccessibilitySettingsUseCase
import com.mysketch.shared.domain.usecase.settings.SyncChatSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsUseCaseModule {

    @Provides
    @Singleton
    fun provideSearchSettingsUseCase(
        settingsRepository: SettingsRepository
    ): SearchSettingsUseCase = SearchSettingsUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideGetContextualHeroCardsUseCase(
        settingsRepository: SettingsRepository
    ): GetContextualHeroCardsUseCase = GetContextualHeroCardsUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideObserveAccessibilitySettingsUseCase(
        settingsRepository: SettingsRepository
    ): ObserveAccessibilitySettingsUseCase = ObserveAccessibilitySettingsUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideSyncAccessibilitySettingsUseCase(
        settingsRepository: SettingsRepository,
        userPreferencesRepository: UserPreferencesRepository,
        authRepository: AuthRepository
    ): SyncAccessibilitySettingsUseCase = SyncAccessibilitySettingsUseCase(settingsRepository, userPreferencesRepository, authRepository)

    @Provides
    @Singleton
    fun provideObserveChatSettingsUseCase(
        settingsRepository: SettingsRepository
    ): ObserveChatSettingsUseCase = ObserveChatSettingsUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideSyncChatSettingsUseCase(
        settingsRepository: SettingsRepository,
        userPreferencesRepository: UserPreferencesRepository,
        authRepository: AuthRepository
    ): SyncChatSettingsUseCase = SyncChatSettingsUseCase(settingsRepository, userPreferencesRepository, authRepository)

    @Provides
    @Singleton
    fun provideGetLinkMetadataUseCase(
        linkPreviewRepository: LinkPreviewRepository
    ): GetLinkMetadataUseCase = GetLinkMetadataUseCase(linkPreviewRepository)

    @Provides
    @Singleton
    fun provideCheckForUpdatesUseCase(
        appRepository: AppRepository,
        platformInfoProvider: PlatformInfoProvider
    ): CheckForUpdatesUseCase = CheckForUpdatesUseCase(appRepository, platformInfoProvider)
}
