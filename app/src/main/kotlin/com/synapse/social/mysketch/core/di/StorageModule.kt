package com.mysketch.core.di

import android.content.Context
import android.content.SharedPreferences
import com.mysketch.data.local.AndroidPlatformInfoProvider
import com.mysketch.data.local.AppSettingsManager
import com.mysketch.data.repository.*
import com.mysketch.data.repository.DomainProfileRepositoryAdapter
import com.mysketch.data.repository.DomainSettingsRepositoryAdapter
import com.mysketch.data.repository.SettingsRepositoryImpl
import com.mysketch.shared.core.media.AndroidMediaCompressor
import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.data.crypto.AndroidSignalProtocolManager
import com.mysketch.shared.data.crypto.SignalProtocolManager
import com.mysketch.shared.data.database.StorageDatabase
import com.mysketch.shared.data.local.AndroidSecureStorage
import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.data.local.database.CachedConversationDao
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.local.database.PendingActionDao
import com.mysketch.shared.data.local.database.PostDao
import com.mysketch.shared.data.local.database.UserDao
import com.mysketch.shared.data.repository.AccountRepository
import com.mysketch.shared.data.repository.AppRepositoryImpl
import com.mysketch.shared.data.repository.BusinessRepositoryImpl
import com.mysketch.shared.data.repository.FollowRepositoryImpl
import com.mysketch.shared.data.repository.LinkPreviewRepositoryImpl
import com.mysketch.shared.data.repository.MediaUploadRepositoryImpl
import com.mysketch.shared.data.repository.OfflineActionRepositoryImpl
import com.mysketch.shared.data.repository.PasskeyRepositoryImpl
import com.mysketch.shared.data.repository.ReelRepository
import com.mysketch.shared.data.repository.StorageRepositoryImpl
import com.mysketch.shared.data.repository.SupabaseAuthRepository
import com.mysketch.shared.data.repository.SupabaseNotificationRepository
import com.mysketch.shared.data.repository.UserPreferencesRepositoryImpl
import com.mysketch.shared.data.repository.UserRepositoryImpl
import com.mysketch.shared.data.source.remote.CloudinaryUploadService
import com.mysketch.shared.data.source.remote.ImgBBUploadService
import com.mysketch.shared.data.source.remote.R2UploadService
import com.mysketch.shared.data.source.remote.SupabaseUploadService
import com.mysketch.shared.domain.repository.AppRepository
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.repository.BusinessRepository
import com.mysketch.shared.domain.repository.FileUploader
import com.mysketch.shared.domain.repository.FollowRepository
import com.mysketch.shared.domain.repository.LinkPreviewRepository
import com.mysketch.shared.domain.repository.MediaUploadRepository
import com.mysketch.shared.domain.repository.NotificationRepository
import com.mysketch.shared.domain.repository.OfflineActionRepository
import com.mysketch.shared.domain.repository.PasskeyRepository
import com.mysketch.shared.domain.repository.PlatformInfoProvider
import com.mysketch.shared.domain.repository.PostActionsRepository
import com.mysketch.shared.domain.repository.StorageRepository
import com.mysketch.shared.domain.repository.SettingsRepository
import com.mysketch.shared.domain.repository.UserPreferencesRepository
import com.mysketch.shared.domain.service.MediaCompressor
import com.mysketch.shared.domain.usecase.CheckForUpdatesUseCase
import com.mysketch.shared.domain.usecase.GetLinkMetadataUseCase
import com.mysketch.shared.domain.usecase.GetStorageConfigUseCase
import com.mysketch.shared.domain.usecase.UpdateStorageProviderUseCase
import com.mysketch.shared.domain.usecase.UploadMediaUseCase
import com.mysketch.shared.domain.usecase.ValidateProviderConfigUseCase
import com.mysketch.shared.domain.usecase.chat.InitializeE2EUseCase
import com.mysketch.shared.domain.usecase.follow.GetFollowersUseCase
import com.mysketch.shared.domain.usecase.follow.GetFollowingUseCase
import com.mysketch.shared.domain.usecase.notification.GetNotificationsUseCase
import com.mysketch.shared.domain.usecase.notification.MarkNotificationAsReadUseCase
import com.mysketch.shared.domain.usecase.notification.SubscribeToNotificationsUseCase
import com.mysketch.shared.domain.usecase.post.DeletePostUseCase
import com.mysketch.shared.domain.usecase.post.TogglePostCommentsUseCase
import com.mysketch.shared.domain.usecase.user.GetCurrentUserAvatarUseCase
import com.mysketch.shared.domain.usecase.user.GetUserProfileUseCase
import com.mysketch.shared.domain.usecase.user.SearchUsersUseCase
import com.mysketch.shared.domain.usecase.user.UpdateProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient as SupabaseClientType
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideFileUploader(@ApplicationContext context: Context): FileUploader {
        return FileUploader(context)
    }
    @Provides
    @Singleton
    fun provideImgBBUploadService(httpClient: HttpClient): ImgBBUploadService {
        return ImgBBUploadService(httpClient)
    }
    @Provides
    @Singleton
    fun provideCloudinaryUploadService(httpClient: HttpClient): CloudinaryUploadService {
        return CloudinaryUploadService(httpClient)
    }
    @Provides
    @Singleton
    fun provideSupabaseUploadService(supabaseClient: SupabaseClientType): SupabaseUploadService {
        return SupabaseUploadService(supabaseClient)
    }
    @Provides
    @Singleton
    fun provideR2UploadService(httpClient: HttpClient): R2UploadService {
        return R2UploadService(httpClient)
    }
    @Provides
    @Singleton
    fun provideValidateProviderConfigUseCase(): ValidateProviderConfigUseCase {
        return ValidateProviderConfigUseCase()
    }
    @Provides
    @Singleton
    fun provideGetStorageConfigUseCase(
        repository: StorageRepository
    ): GetStorageConfigUseCase {
        return GetStorageConfigUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideUpdateStorageProviderUseCase(
        repository: StorageRepository
    ): UpdateStorageProviderUseCase {
        return UpdateStorageProviderUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideMediaCompressor(@ApplicationContext context: Context): MediaCompressor {
        return AndroidMediaCompressor(context)
    }
    @Provides
    @Singleton
    fun provideUploadMediaUseCase(
        repository: StorageRepository,
        settingsRepository: com.mysketch.shared.domain.repository.SettingsRepository,
        mediaUploadRepository: MediaUploadRepository,
        mediaCompressor: MediaCompressor
    ): UploadMediaUseCase {
        return UploadMediaUseCase(
            repository,
            settingsRepository,
            mediaUploadRepository,
            mediaCompressor
        )
    }

}

