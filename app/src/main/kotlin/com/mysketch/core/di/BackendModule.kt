package com.mysketch.core.di

import com.mysketch.data.repository.*
import com.mysketch.data.source.CommentLocalDataSource
import com.mysketch.data.source.CommentRemoteDataSource
import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.data.crypto.SignalProtocolManager
import com.mysketch.shared.data.datasource.SupabaseBlockDataSource
import com.mysketch.shared.data.datasource.SupabaseChatDataSource
import com.mysketch.shared.data.datasource.SupabaseUserDataSource
import com.mysketch.shared.data.database.StorageDatabase
import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.data.local.database.CachedConversationDao
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.local.database.PendingActionDao
import com.mysketch.shared.data.local.database.PostDao
import com.mysketch.shared.data.local.database.StoryDao
import com.mysketch.shared.data.local.database.UserDao
import com.mysketch.shared.data.repository.*
import com.mysketch.shared.data.sync.SyncManager
import com.mysketch.shared.data.sync.SupabaseMessageDeliveryCoordinator
import com.mysketch.shared.domain.repository.*
import com.mysketch.shared.domain.usecase.UploadMediaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient as SupabaseClientType
import io.ktor.client.HttpClient
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mysketch.data.auth.FirebaseAuthRepository

@Module
@InstallIn(SingletonComponent::class)
object BackendModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClientType {
        return SupabaseClient.client
    }

    @Provides
    @Singleton
    fun provideChatRepository(
        client: SupabaseClientType,
        signalProtocolManager: SignalProtocolManager,
        mediaUploadRepository: MediaUploadRepository,
        presenceRepository: PresenceRepository,
        offlineActionRepository: OfflineActionRepository,
        cachedMessageDao: CachedMessageDao,
        cachedConversationDao: CachedConversationDao,
        secureStorage: SecureStorage
    ): ChatRepository {
        return SupabaseChatRepository(
            SupabaseChatDataSource(client),
            client,
            signalProtocolManager,
            mediaUploadRepository,
            presenceRepository,
            offlineActionRepository,
            cachedMessageDao,
            cachedConversationDao,
            secureStorage = secureStorage
        )
    }

    @Provides
    @Singleton
    fun provideSharedAuthRepository(
        cachedMessageDao: CachedMessageDao,
        cachedConversationDao: CachedConversationDao,
        postDao: PostDao,
        storyDao: StoryDao,
        offlineActionRepository: OfflineActionRepository,
        syncManager: SyncManager
    ): AuthRepository {
        return FirebaseAuthRepository(
            auth = FirebaseAuth.getInstance(),
            firestore = FirebaseFirestore.getInstance(),
            cachedMessageDao = cachedMessageDao,
            cachedConversationDao = cachedConversationDao,
            postDao = postDao,
            storyDao = storyDao,
            offlineActionRepository = offlineActionRepository,
            syncManager = syncManager
        )
    }

    @Provides
    @Singleton
    fun provideMessageDeliveryCoordinator(
        client: SupabaseClientType,
        authRepository: AuthRepository
    ): MessageDeliveryCoordinator = SupabaseMessageDeliveryCoordinator(client, authRepository)

    @Provides
    @Singleton
    fun providePresenceRepository(): PresenceRepository = SupabasePresenceRepository()

    @Provides
    @Singleton
    fun provideSearchRepository(): ISearchRepository = SearchRepositoryImpl()

    @Provides
    @Singleton
    fun provideAccountRepository(client: SupabaseClientType): AccountRepository = AccountRepository(client)

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao, client: SupabaseClientType): com.mysketch.data.repository.UserRepositoryImpl {
        return com.mysketch.data.repository.UserRepositoryImpl(userDao, client)
    }

    @Provides
    @Singleton
    fun provideSharedUserRepository(storageDatabase: StorageDatabase, client: SupabaseClientType): com.mysketch.shared.domain.repository.UserRepository {
        val userDataSource = SupabaseUserDataSource(client)
        val legacyRepository = com.mysketch.shared.data.repository.UserRepositoryImpl(storageDatabase, userDataSource)
        return com.mysketch.data.auth.FirebaseUsernameAvailabilityRepository(
            delegate = legacyRepository,
            firestore = FirebaseFirestore.getInstance()
        )
    }

    @Provides
    @Singleton
    fun provideUsernameRepository(): UsernameRepositoryImpl = UsernameRepositoryImpl()

    @Provides
    @Singleton
    fun provideProfileRepository(client: SupabaseClientType, commentRepository: CommentRepositoryImpl): com.mysketch.data.repository.ProfileRepository {
        return ProfileRepositoryImpl(client, commentRepository)
    }

    @Provides
    @Singleton
    fun provideFollowRepository(client: SupabaseClientType): FollowRepository = FollowRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(client: io.github.jan.supabase.SupabaseClient): UserPreferencesRepository = UserPreferencesRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideOfflineActionRepository(pendingActionDao: PendingActionDao): OfflineActionRepository = OfflineActionRepositoryImpl(pendingActionDao)

    @Provides
    @Singleton
    fun providePostRepository(postDao: PostDao, client: SupabaseClientType, offlineActionRepository: OfflineActionRepository): PostRepositoryImpl {
        return PostRepositoryImpl(postDao, client, offlineActionRepository)
    }

    @Provides
    @Singleton
    fun provideDomainPostRepository(postRepository: PostRepositoryImpl): com.mysketch.domain.repository.PostRepository = postRepository

    @Provides
    @Singleton
    fun providePostActionsRepository(postRepository: PostRepositoryImpl): PostActionsRepository = postRepository

    @Provides
    @Singleton
    fun providePostInteractionRepository(): com.mysketch.domain.repository.PostInteractionRepository = PostInteractionRepositoryImpl()

    @Provides
    @Singleton
    fun provideProfileActionRepository(): ProfileActionRepositoryImpl = ProfileActionRepositoryImpl()

    @Provides
    @Singleton
    fun provideReactionRepository(client: SupabaseClientType): com.mysketch.domain.repository.ReactionRepository = ReactionRepositoryImpl(client)

    @Provides
    @Singleton
    fun providePostDetailRepository(client: SupabaseClientType, reactionRepository: ReactionRepositoryImpl): PostDetailRepositoryImpl = PostDetailRepositoryImpl(client, reactionRepository)

    @Provides
    @Singleton
    fun provideCommentRemoteDataSource(client: SupabaseClientType): CommentRemoteDataSource = CommentRemoteDataSource(client)

    @Provides
    @Singleton
    fun provideCommentLocalDataSource(storageDatabase: StorageDatabase, postDao: PostDao): CommentLocalDataSource = CommentLocalDataSource(storageDatabase, postDao)

    @Provides
    @Singleton
    fun provideCommentRepository(
        localDataSource: CommentLocalDataSource,
        remoteDataSource: CommentRemoteDataSource,
        userRepository: com.mysketch.data.repository.UserRepositoryImpl,
        reactionRepository: ReactionRepositoryImpl,
        @Named("ApplicationScope") externalScope: CoroutineScope
    ): CommentRepositoryImpl {
        return CommentRepositoryImpl(localDataSource, remoteDataSource, userRepository, externalScope, reactionRepository)
    }

    @Provides
    @Singleton
    fun providePollRepository(client: SupabaseClientType): com.mysketch.domain.repository.PollRepository = PollRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideConcretePollRepository(client: SupabaseClientType): PollRepositoryImpl = PollRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideBookmarkRepository(client: SupabaseClientType): BookmarkRepositoryImpl = BookmarkRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideReportRepository(client: SupabaseClientType): com.mysketch.domain.repository.ReportRepository = ReportRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideStoryRepository(@ApplicationContext context: android.content.Context, uploadMediaUseCase: UploadMediaUseCase): com.mysketch.data.repository.StoryRepository { return com.mysketch.data.repository.StoryRepositoryImpl(context, uploadMediaUseCase) }

    @Provides
    @Singleton
    fun provideReelRepository(): ReelRepository = ReelRepository()

@Provides
    @Singleton
    fun provideLinkPreviewRepository(database: StorageDatabase, httpClient: HttpClient): LinkPreviewRepository = LinkPreviewRepositoryImpl(database, httpClient)

    @Provides
    @Singleton
    fun provideScheduledPostRepository(client: SupabaseClientType): com.mysketch.domain.repository.ScheduledPostRepository = ScheduledPostRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideAppRepository(httpClient: HttpClient): AppRepository = AppRepositoryImpl(httpClient)

    @Provides
    @Singleton
    fun provideBusinessRepository(client: SupabaseClientType): BusinessRepository = BusinessRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideStorageRepository(db: StorageDatabase, secureStorage: SecureStorage): StorageRepository = StorageRepositoryImpl(db, secureStorage)

    @Provides
    @Singleton
fun provideMediaUploadRepository(
        fileUploader: FileUploader,
        imgBBUploadService: com.mysketch.shared.data.source.remote.ImgBBUploadService,
        cloudinaryUploadService: com.mysketch.shared.data.source.remote.CloudinaryUploadService,
        supabaseUploadService: com.mysketch.shared.data.source.remote.SupabaseUploadService,
        r2UploadService: com.mysketch.shared.data.source.remote.R2UploadService
    ): MediaUploadRepository = MediaUploadRepositoryImpl(fileUploader, imgBBUploadService, cloudinaryUploadService, supabaseUploadService, r2UploadService)

    @Provides
    @Singleton
    fun provideNotificationRepository(client: SupabaseClientType, @Named("ApplicationScope") externalScope: CoroutineScope): NotificationRepository = SupabaseNotificationRepository(client, externalScope)

    @Provides
    @Singleton
    fun provideBlockingRepository(dataSource: SupabaseBlockDataSource): BlockRepository = BlockRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideChatMessagingRepository(client: SupabaseClientType): ChatMessagingRepositoryImpl = ChatMessagingRepositoryImpl(client)

    @Provides
    @Singleton
    fun provideChatRepositoryApp(
        @ApplicationContext context: android.content.Context,
        signalRepository: com.mysketch.shared.data.repository.SignalRepository,
        signalProtocolManager: SignalProtocolManager,
        client: SupabaseClientType
    ): ChatRepositoryImpl = ChatRepositoryImpl(client, signalRepository, signalProtocolManager, computeDeviceId(context))

    private fun computeDeviceId(context: android.content.Context): Int {
        val androidId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
        return (androidId ?: context.packageName).hashCode()
    }

@Provides
    @Singleton
    fun provideChatVerificationRepository(
        @ApplicationContext context: android.content.Context,
        client: SupabaseClientType,
        signalProtocolManager: SignalProtocolManager,
        chatRepository: com.mysketch.shared.domain.repository.ChatRepository
    ): com.mysketch.domain.repository.ChatVerificationRepository =
        com.mysketch.data.repository.ChatVerificationRepositoryImpl(context, client, signalProtocolManager, chatRepository)

    @Provides
    @Singleton
    fun provideDraftRepository(application: android.app.Application): DraftRepositoryImpl = DraftRepositoryImpl(application)

    @Provides
    @Singleton
    fun provideEditProfileRepository(
        @ApplicationContext context: android.content.Context,
        supabaseStorageService: com.mysketch.data.remote.services.SupabaseStorageService
    ): EditProfileRepositoryImpl = EditProfileRepositoryImpl(context, supabaseStorageService)

    @Provides
    @Singleton
    fun provideFeedbackRepository(): FeedbackRepositoryImpl = FeedbackRepositoryImpl()

    @Provides
    @Singleton
    fun provideLocationRepository(): LocationRepositoryImpl = LocationRepositoryImpl()

    @Provides
    @Singleton
    fun provideSettingsRepositoryImpl(@ApplicationContext context: android.content.Context): SettingsRepositoryImpl = SettingsRepositoryImpl.getInstance(context)
}













