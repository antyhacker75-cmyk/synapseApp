package com.mysketch.core.di

import android.content.Context
import android.content.SharedPreferences
import com.mysketch.data.local.AndroidPlatformInfoProvider
import com.mysketch.data.local.AppSettingsManager
import com.mysketch.data.repository.*
import com.mysketch.data.repository.DomainProfileRepositoryAdapter
import com.mysketch.data.repository.DomainSettingsRepositoryAdapter
import com.mysketch.data.repository.SettingsRepository
import com.mysketch.data.repository.SettingsRepositoryImpl
import com.mysketch.shared.core.media.AndroidMediaCompressor
import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.data.crypto.AndroidSignalProtocolManager
import com.mysketch.shared.data.crypto.SignalProtocolManager
import com.mysketch.shared.data.database.StorageDatabase
import com.mysketch.shared.data.datasource.SupabaseChatDataSource
import com.mysketch.shared.data.local.AndroidSecureStorage
import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.data.local.database.CachedConversationDao
import com.mysketch.shared.data.local.database.CachedMessageDao
import com.mysketch.shared.data.local.database.PendingActionDao
import com.mysketch.shared.data.local.database.PostDao
import com.mysketch.shared.data.local.database.UserDao
import com.mysketch.shared.domain.usecase.presence.ObserveUserActiveStatusUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserPresenceDetailUseCase
import com.mysketch.shared.domain.usecase.presence.UpdateCurrentChatUseCase
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
import com.mysketch.shared.data.repository.SupabaseChatRepository
import com.mysketch.shared.data.repository.SupabaseNotificationRepository
import com.mysketch.shared.data.repository.UserPreferencesRepositoryImpl
import com.mysketch.shared.data.repository.UserRepositoryImpl
import com.mysketch.shared.data.source.remote.CloudinaryUploadService
import com.mysketch.shared.data.source.remote.ImgBBUploadService
import com.mysketch.shared.data.source.remote.R2UploadService
import com.mysketch.shared.data.source.remote.SupabaseUploadService
import com.mysketch.feature.inbox.inbox.voice.VoiceUploadService
import com.mysketch.shared.domain.repository.AppRepository
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.repository.BusinessRepository
import com.mysketch.shared.domain.repository.ChatRepository
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
import com.mysketch.shared.domain.repository.UserPreferencesRepository
import com.mysketch.shared.domain.service.MediaCompressor
import com.mysketch.shared.domain.usecase.CheckForUpdatesUseCase
import com.mysketch.shared.domain.usecase.GetLinkMetadataUseCase
import com.mysketch.shared.domain.usecase.GetStorageConfigUseCase
import com.mysketch.shared.domain.usecase.UpdateStorageProviderUseCase
import com.mysketch.shared.domain.usecase.ValidateProviderConfigUseCase
import com.mysketch.shared.domain.usecase.chat.BroadcastTypingStatusUseCase
import com.mysketch.shared.domain.usecase.chat.BulkDeleteMessagesForMeUseCase
import com.mysketch.shared.domain.usecase.chat.BulkDeleteMessagesUseCase
import com.mysketch.shared.domain.usecase.chat.ClearChatHistoryUseCase
import com.mysketch.shared.domain.usecase.chat.ReportChatUseCase
import com.mysketch.shared.domain.usecase.blocking.BlockUserUseCase
import com.mysketch.domain.usecase.profile.ReportUserUseCase
import com.mysketch.shared.domain.usecase.chat.DeleteMessageForMeUseCase
import com.mysketch.shared.domain.usecase.chat.DeleteMessageUseCase
import com.mysketch.shared.domain.usecase.chat.EditMessageUseCase
import com.mysketch.shared.domain.usecase.chat.ExportChatUseCase
import com.mysketch.shared.domain.usecase.chat.GetCommonGroupsUseCase
import com.mysketch.shared.domain.usecase.chat.GetConversationMuteStatusUseCase
import com.mysketch.shared.domain.usecase.chat.GetConversationsUseCase
import com.mysketch.shared.domain.usecase.chat.GetDisappearingModeUseCase
import com.mysketch.shared.domain.usecase.chat.GetMessageByIdUseCase
import com.mysketch.shared.domain.usecase.chat.GetMessagesUseCase
import com.mysketch.shared.domain.usecase.chat.GetSharedContentSummaryUseCase
import com.mysketch.shared.domain.usecase.chat.GetSharedContentUseCase
import com.mysketch.shared.domain.usecase.chat.InitializeE2EUseCase
import com.mysketch.shared.domain.usecase.chat.MarkIncomingMessageAsDeliveredUseCase
import com.mysketch.shared.domain.usecase.chat.MarkMessagesAsDeliveredUseCase
import com.mysketch.shared.domain.usecase.chat.MarkMessagesAsReadUseCase
import com.mysketch.shared.domain.usecase.chat.ResetE2EUseCase
import com.mysketch.shared.domain.usecase.chat.SearchMessagesUseCase
import com.mysketch.shared.domain.usecase.chat.SendMessageUseCase
import com.mysketch.shared.domain.usecase.chat.CreatePollUseCase
import com.mysketch.shared.domain.usecase.chat.SetConversationMuteStatusUseCase
import com.mysketch.shared.domain.usecase.chat.SetDisappearingModeUseCase
import com.mysketch.shared.domain.usecase.chat.SharePostToChatUseCase
import com.mysketch.shared.domain.usecase.chat.SubscribeToInboxUpdatesUseCase
import com.mysketch.shared.domain.usecase.chat.SubscribeToRosterChangesUseCase
import com.mysketch.shared.domain.usecase.chat.SubscribeToMessagesUseCase
import com.mysketch.shared.domain.usecase.chat.SubscribeToTypingStatusUseCase
import com.mysketch.shared.domain.usecase.chat.UploadMediaUseCase
import com.mysketch.shared.domain.usecase.chat.ToggleMessageReactionUseCase
    import com.mysketch.shared.domain.usecase.chat.GetMessageReactionsUseCase
import com.mysketch.shared.domain.usecase.chat.PopulateMessageReactionsUseCase
import com.mysketch.shared.domain.usecase.chat.ReconcileRealtimeMessageUseCase
import com.mysketch.shared.domain.usecase.chat.SubscribeToMessageReactionsUseCase
    import com.mysketch.shared.domain.usecase.chat.VoteInPollUseCase
    import com.mysketch.shared.domain.usecase.chat.MarkViewOnceConsumedUseCase
import com.mysketch.shared.domain.usecase.follow.GetFollowersUseCase
import com.mysketch.shared.domain.usecase.follow.GetFollowingUseCase
import com.mysketch.shared.domain.usecase.notification.GetNotificationsUseCase
import com.mysketch.shared.domain.usecase.notification.MarkNotificationAsReadUseCase
import com.mysketch.shared.domain.usecase.notification.SubscribeToNotificationsUseCase
import com.mysketch.shared.domain.usecase.post.DeletePostUseCase
import com.mysketch.shared.domain.usecase.post.TogglePostCommentsUseCase
import com.mysketch.shared.domain.usecase.settings.ObserveChatSettingsUseCase
import com.mysketch.shared.domain.usecase.settings.SyncChatSettingsUseCase
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

import com.mysketch.feature.inbox.inbox.ChatAiDependencies
import com.mysketch.feature.inbox.inbox.ChatDependencies
import com.mysketch.feature.inbox.inbox.ChatMediaDependencies
import com.mysketch.feature.inbox.inbox.ChatMessagingDependencies
import com.mysketch.feature.inbox.inbox.ChatSettingsDependencies
import com.mysketch.feature.inbox.inbox.ChatSubscriptionDependencies

import com.mysketch.shared.domain.usecase.ai.GenerateSmartRepliesUseCase
import com.mysketch.domain.usecase.ai.SummarizeChatUseCase
import com.mysketch.domain.usecase.ai.SummarizeMessageUseCase
import com.mysketch.core.util.UploadProgressManager
import com.mysketch.shared.domain.usecase.chat.GetChatSettingsUseCase


@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideUploadMediaUseCase(
        chatRepository: ChatRepository,
        storageRepository: StorageRepository,
        mediaUploadRepository: MediaUploadRepository,
        fileUploader: FileUploader
    ): UploadMediaUseCase = UploadMediaUseCase(chatRepository, storageRepository, mediaUploadRepository, fileUploader)

    @Provides
    @Singleton
    fun provideGetDisappearingModeUseCase(chatRepository: ChatRepository): GetDisappearingModeUseCase = GetDisappearingModeUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideDeleteExpiredMessagesUseCase(chatRepository: ChatRepository): com.mysketch.shared.domain.usecase.chat.DeleteExpiredMessagesUseCase = com.mysketch.shared.domain.usecase.chat.DeleteExpiredMessagesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSetDisappearingModeUseCase(chatRepository: ChatRepository): com.mysketch.shared.domain.usecase.chat.SetDisappearingModeUseCase {
        val addSystemMessageUseCase = com.mysketch.shared.domain.usecase.chat.AddSystemMessageUseCase(chatRepository)
        return com.mysketch.shared.domain.usecase.chat.SetDisappearingModeUseCase(chatRepository, addSystemMessageUseCase)
    }

    @Provides
    @Singleton
    fun provideClearChatHistoryUseCase(chatRepository: ChatRepository): ClearChatHistoryUseCase = ClearChatHistoryUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideReportChatUseCase(chatRepository: ChatRepository): ReportChatUseCase = ReportChatUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetSharedContentSummaryUseCase(chatRepository: ChatRepository): GetSharedContentSummaryUseCase = GetSharedContentSummaryUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetSharedContentUseCase(chatRepository: ChatRepository): GetSharedContentUseCase = GetSharedContentUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetConversationMuteStatusUseCase(chatRepository: ChatRepository): GetConversationMuteStatusUseCase = GetConversationMuteStatusUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSetConversationMuteStatusUseCase(chatRepository: ChatRepository): SetConversationMuteStatusUseCase = SetConversationMuteStatusUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetCommonGroupsUseCase(chatRepository: ChatRepository): GetCommonGroupsUseCase = GetCommonGroupsUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetMessagesUseCase(chatRepository: ChatRepository): GetMessagesUseCase = GetMessagesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSearchMessagesUseCase(chatRepository: ChatRepository): SearchMessagesUseCase = SearchMessagesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideExportChatUseCase(chatRepository: ChatRepository): ExportChatUseCase = ExportChatUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSendMessageUseCase(chatRepository: ChatRepository, signalProtocolManager: SignalProtocolManager): SendMessageUseCase = SendMessageUseCase(chatRepository, signalProtocolManager)

    @Provides
    @Singleton
    fun provideSharePostToChatUseCase(sendMessageUseCase: SendMessageUseCase): SharePostToChatUseCase = SharePostToChatUseCase(sendMessageUseCase)

    @Provides
    @Singleton
    fun provideCreatePollUseCase(sendMessageUseCase: SendMessageUseCase): CreatePollUseCase = CreatePollUseCase(sendMessageUseCase)

    @Provides
    @Singleton
    fun provideSubscribeToMessagesUseCase(chatRepository: ChatRepository): SubscribeToMessagesUseCase = SubscribeToMessagesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideMarkMessagesAsReadUseCase(chatRepository: ChatRepository): MarkMessagesAsReadUseCase = MarkMessagesAsReadUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideMarkMessagesAsDeliveredUseCase(chatRepository: ChatRepository): MarkMessagesAsDeliveredUseCase = MarkMessagesAsDeliveredUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideMarkIncomingMessageAsDeliveredUseCase(chatRepository: ChatRepository): MarkIncomingMessageAsDeliveredUseCase = MarkIncomingMessageAsDeliveredUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideBroadcastTypingStatusUseCase(chatRepository: ChatRepository): BroadcastTypingStatusUseCase = BroadcastTypingStatusUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSubscribeToTypingStatusUseCase(chatRepository: ChatRepository): SubscribeToTypingStatusUseCase = SubscribeToTypingStatusUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideEditMessageUseCase(chatRepository: ChatRepository): EditMessageUseCase = EditMessageUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideDeleteMessageUseCase(chatRepository: ChatRepository): DeleteMessageUseCase = DeleteMessageUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideDeleteMessageForMeUseCase(chatRepository: ChatRepository): DeleteMessageForMeUseCase = DeleteMessageForMeUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideBulkDeleteMessagesUseCase(chatRepository: ChatRepository): BulkDeleteMessagesUseCase = BulkDeleteMessagesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideBulkDeleteMessagesForMeUseCase(chatRepository: ChatRepository): BulkDeleteMessagesForMeUseCase = BulkDeleteMessagesForMeUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideInitializeE2EUseCase(chatRepository: ChatRepository): InitializeE2EUseCase = InitializeE2EUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetMessageByIdUseCase(chatRepository: ChatRepository): GetMessageByIdUseCase = GetMessageByIdUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideToggleMessageReactionUseCase(chatRepository: ChatRepository): ToggleMessageReactionUseCase = ToggleMessageReactionUseCase(chatRepository)

    @Provides
    @Singleton
    fun providePopulateMessageReactionsUseCase(chatRepository: ChatRepository): PopulateMessageReactionsUseCase = PopulateMessageReactionsUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSubscribeToMessageReactionsUseCase(chatRepository: ChatRepository): SubscribeToMessageReactionsUseCase = SubscribeToMessageReactionsUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideVoteInPollUseCase(chatRepository: ChatRepository): VoteInPollUseCase = VoteInPollUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideMarkViewOnceConsumedUseCase(chatRepository: ChatRepository): MarkViewOnceConsumedUseCase = MarkViewOnceConsumedUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideReconcileRealtimeMessageUseCase(chatRepository: ChatRepository): ReconcileRealtimeMessageUseCase = ReconcileRealtimeMessageUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideResetE2EUseCase(chatRepository: ChatRepository): ResetE2EUseCase = ResetE2EUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetConversationsUseCase(chatRepository: ChatRepository): GetConversationsUseCase = GetConversationsUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSubscribeToInboxUpdatesUseCase(chatRepository: ChatRepository): SubscribeToInboxUpdatesUseCase = SubscribeToInboxUpdatesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideSubscribeToRosterChangesUseCase(chatRepository: ChatRepository): SubscribeToRosterChangesUseCase = SubscribeToRosterChangesUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideChatDependencies(
        messaging: ChatMessagingDependencies,
        subscription: ChatSubscriptionDependencies,
        ai: ChatAiDependencies,
        media: ChatMediaDependencies,
        settings: ChatSettingsDependencies,
        initializeE2EUseCase: InitializeE2EUseCase,
        getOrCreateChatUseCase: com.mysketch.shared.domain.usecase.chat.GetOrCreateChatUseCase,
        getChatInfoUseCase: com.mysketch.shared.domain.usecase.chat.GetChatInfoUseCase,
        getGroupMembersUseCase: com.mysketch.shared.domain.usecase.chat.GetGroupMembersUseCase,
        getMessageByIdUseCase: com.mysketch.shared.domain.usecase.chat.GetMessageByIdUseCase,
        toggleMessageReactionUseCase: ToggleMessageReactionUseCase,
        observeUserActiveStatusUseCase: ObserveUserActiveStatusUseCase,
        observeUserPresenceDetailUseCase: ObserveUserPresenceDetailUseCase,
        updateCurrentChatUseCase: UpdateCurrentChatUseCase,
        voteInPollUseCase: VoteInPollUseCase,
        markViewOnceConsumedUseCase: MarkViewOnceConsumedUseCase,
        searchMessagesUseCase: SearchMessagesUseCase,
        exportChatUseCase: ExportChatUseCase,
        reportChatUseCase: ReportChatUseCase,
        clearChatHistoryUseCase: ClearChatHistoryUseCase,
        blockUserUseCase: BlockUserUseCase,
        reportUserUseCase: ReportUserUseCase
    ): ChatDependencies = ChatDependencies(
        messaging = messaging,
        subscription = subscription,
        ai = ai,
        media = media,
        settings = settings,
        initializeE2EUseCase = initializeE2EUseCase,
        getOrCreateChatUseCase = getOrCreateChatUseCase,
        getChatInfoUseCase = getChatInfoUseCase,
        getGroupMembersUseCase = getGroupMembersUseCase,
        getMessageByIdUseCase = getMessageByIdUseCase,
        toggleMessageReactionUseCase = toggleMessageReactionUseCase,
        observeUserActiveStatusUseCase = observeUserActiveStatusUseCase,
        observeUserPresenceDetailUseCase = observeUserPresenceDetailUseCase,
        updateCurrentChatUseCase = updateCurrentChatUseCase,
        voteInPollUseCase = voteInPollUseCase,
        markViewOnceConsumedUseCase = markViewOnceConsumedUseCase,
        searchMessagesUseCase = searchMessagesUseCase,
        exportChatUseCase = exportChatUseCase,
        reportChatUseCase = reportChatUseCase,
        clearChatHistoryUseCase = clearChatHistoryUseCase,
        blockUserUseCase = blockUserUseCase,
        reportUserUseCase = reportUserUseCase
    )

    // Messaging dependencies group
    @Provides
    @Singleton
    fun provideChatMessagingDependencies(
        sendMessageUseCase: SendMessageUseCase,
        editMessageUseCase: EditMessageUseCase,
        deleteMessageUseCase: DeleteMessageUseCase,
        deleteMessageForMeUseCase: DeleteMessageForMeUseCase,
        bulkDeleteMessagesUseCase: BulkDeleteMessagesUseCase,
        bulkDeleteMessagesForMeUseCase: BulkDeleteMessagesForMeUseCase,
        populateMessageReactionsUseCase: PopulateMessageReactionsUseCase,
        currentUserIdUseCase: com.mysketch.shared.domain.usecase.chat.GetCurrentUserIdUseCase,
        reconcileRealtimeMessageUseCase: ReconcileRealtimeMessageUseCase,
        chatRepository: ChatRepository,
        authRepository: com.mysketch.shared.domain.repository.AuthRepository
    ): ChatMessagingDependencies = ChatMessagingDependencies(
        sendMessageUseCase = sendMessageUseCase,
        editMessageUseCase = editMessageUseCase,
        deleteMessageUseCase = deleteMessageUseCase,
        deleteMessageForMeUseCase = deleteMessageForMeUseCase,
        bulkDeleteMessagesUseCase = bulkDeleteMessagesUseCase,
        bulkDeleteMessagesForMeUseCase = bulkDeleteMessagesForMeUseCase,
        populateMessageReactionsUseCase = populateMessageReactionsUseCase,
        currentUserIdUseCase = currentUserIdUseCase,
        reconcileRealtimeMessageUseCase = reconcileRealtimeMessageUseCase,
        chatRepository = chatRepository,
        authRepository = authRepository
    )

    // Subscription dependencies group
    @Provides
    @Singleton
    fun provideChatSubscriptionDependencies(
        subscribeToMessagesUseCase: SubscribeToMessagesUseCase,
        subscribeToTypingStatusUseCase: SubscribeToTypingStatusUseCase,
        subscribeToMessageReactionsUseCase: SubscribeToMessageReactionsUseCase,
        markMessagesAsReadUseCase: MarkMessagesAsReadUseCase,
        markMessagesAsDeliveredUseCase: MarkMessagesAsDeliveredUseCase,
        getMessagesUseCase: GetMessagesUseCase
    ): ChatSubscriptionDependencies = ChatSubscriptionDependencies(
        subscribeToMessagesUseCase = subscribeToMessagesUseCase,
        subscribeToTypingStatusUseCase = subscribeToTypingStatusUseCase,
        subscribeToMessageReactionsUseCase = subscribeToMessageReactionsUseCase,
        markMessagesAsReadUseCase = markMessagesAsReadUseCase,
        markMessagesAsDeliveredUseCase = markMessagesAsDeliveredUseCase,
        getMessagesUseCase = getMessagesUseCase
    )

    // AI dependencies group
    @Provides
    @Singleton
    fun provideChatAiDependencies(
        generateSmartRepliesUseCase: GenerateSmartRepliesUseCase,
        summarizeChatUseCase: com.mysketch.domain.usecase.ai.SummarizeChatUseCase,
        summarizeMessageUseCase: com.mysketch.domain.usecase.ai.SummarizeMessageUseCase,
        getUserProfileUseCase: GetUserProfileUseCase
    ): ChatAiDependencies = ChatAiDependencies(
        generateSmartRepliesUseCase = generateSmartRepliesUseCase,
        summarizeChatUseCase = summarizeChatUseCase,
        summarizeMessageUseCase = summarizeMessageUseCase,
        getUserProfileUseCase = getUserProfileUseCase
    )

    // Media dependencies group
    @Provides
    @Singleton
    fun provideChatMediaDependencies(
        uploadMediaUseCase: UploadMediaUseCase,
        fileUploader: FileUploader,
        uploadProgressManager: UploadProgressManager,
        voiceUploadService: VoiceUploadService,
        mediaCompressor: MediaCompressor?,
        getStorageConfigUseCase: GetStorageConfigUseCase
    ): ChatMediaDependencies = ChatMediaDependencies(
        uploadMediaUseCase = uploadMediaUseCase,
        fileUploader = fileUploader,
        uploadProgressManager = uploadProgressManager,
        voiceUploadService = voiceUploadService,
        mediaCompressor = mediaCompressor,
        getStorageConfigUseCase = getStorageConfigUseCase
    )

    // Settings dependencies group
    @Provides
    @Singleton
    fun provideChatSettingsDependencies(
        setDisappearingModeUseCase: SetDisappearingModeUseCase,
        getDisappearingModeUseCase: com.mysketch.shared.domain.usecase.chat.GetDisappearingModeUseCase,
        getChatSettingsUseCase: com.mysketch.shared.domain.usecase.chat.GetChatSettingsUseCase,
        getConversationMuteStatusUseCase: com.mysketch.shared.domain.usecase.chat.GetConversationMuteStatusUseCase,
        setConversationMuteStatusUseCase: SetConversationMuteStatusUseCase,
        chatLockManager: com.mysketch.core.util.ChatLockManager,
        deleteExpiredMessagesUseCase: com.mysketch.shared.domain.usecase.chat.DeleteExpiredMessagesUseCase,
        settingsRepository: SettingsRepository
    ): ChatSettingsDependencies = ChatSettingsDependencies(
        setDisappearingModeUseCase = setDisappearingModeUseCase,
        getDisappearingModeUseCase = getDisappearingModeUseCase,
        getChatSettingsUseCase = getChatSettingsUseCase,
        getConversationMuteStatusUseCase = getConversationMuteStatusUseCase,
        setConversationMuteStatusUseCase = setConversationMuteStatusUseCase,
        chatLockManager = chatLockManager,
        deleteExpiredMessagesUseCase = deleteExpiredMessagesUseCase,
        settingsRepository = settingsRepository
    )
}
