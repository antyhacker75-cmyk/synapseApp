package com.mysketch.feature.inbox.inbox

import com.mysketch.core.util.UploadProgressManager
import com.mysketch.shared.domain.model.settings.MediaUploadQuality
import com.mysketch.shared.domain.repository.FileUploader
import com.mysketch.shared.domain.service.MediaCompressor
import com.mysketch.shared.domain.usecase.ai.GenerateSmartRepliesUseCase
import com.mysketch.shared.domain.usecase.chat.*
import com.mysketch.shared.domain.usecase.blocking.BlockUserUseCase
import com.mysketch.domain.usecase.profile.ReportUserUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserActiveStatusUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserPresenceDetailUseCase
import com.mysketch.shared.domain.usecase.presence.UpdateCurrentChatUseCase
import com.mysketch.shared.domain.usecase.user.GetUserProfileUseCase
import com.mysketch.shared.domain.usecase.GetStorageConfigUseCase
import com.mysketch.feature.inbox.inbox.voice.VoiceUploadService

/**
 * Grouped dependencies for messaging concerns.
 */
data class ChatMessagingDependencies(
    val sendMessageUseCase: SendMessageUseCase,
    val editMessageUseCase: EditMessageUseCase,
    val deleteMessageUseCase: DeleteMessageUseCase,
    val deleteMessageForMeUseCase: DeleteMessageForMeUseCase,
    val bulkDeleteMessagesUseCase: BulkDeleteMessagesUseCase,
    val bulkDeleteMessagesForMeUseCase: BulkDeleteMessagesForMeUseCase,
    val populateMessageReactionsUseCase: PopulateMessageReactionsUseCase,
    val currentUserIdUseCase: com.mysketch.shared.domain.usecase.chat.GetCurrentUserIdUseCase,
    val reconcileRealtimeMessageUseCase: ReconcileRealtimeMessageUseCase,
    val chatRepository: com.mysketch.shared.domain.repository.ChatRepository,
    val authRepository: com.mysketch.shared.domain.repository.AuthRepository,
)

/**
 * Grouped dependencies for realtime subscription concerns.
 */
data class ChatSubscriptionDependencies(
    val subscribeToMessagesUseCase: SubscribeToMessagesUseCase,
    val subscribeToTypingStatusUseCase: SubscribeToTypingStatusUseCase,
    val subscribeToMessageReactionsUseCase: SubscribeToMessageReactionsUseCase,
    val markMessagesAsReadUseCase: MarkMessagesAsReadUseCase,
    val markMessagesAsDeliveredUseCase: MarkMessagesAsDeliveredUseCase,
    val getMessagesUseCase: GetMessagesUseCase,
)

/**
 * Grouped dependencies for AI features.
 */
data class ChatAiDependencies(
    val generateSmartRepliesUseCase: GenerateSmartRepliesUseCase,
    val summarizeChatUseCase: com.mysketch.domain.usecase.ai.SummarizeChatUseCase,
    val summarizeMessageUseCase: com.mysketch.domain.usecase.ai.SummarizeMessageUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase,
)

/**
 * Grouped dependencies for media upload concerns.
 */
data class ChatMediaDependencies(
    val uploadMediaUseCase: UploadMediaUseCase,
    val fileUploader: FileUploader,
    val uploadProgressManager: UploadProgressManager,
    val voiceUploadService: VoiceUploadService,
    val mediaCompressor: MediaCompressor?,
    val getStorageConfigUseCase: GetStorageConfigUseCase,
)

/**
 * Grouped dependencies for settings concerns.
 */
data class ChatSettingsDependencies(
    val setDisappearingModeUseCase: SetDisappearingModeUseCase,
    val getDisappearingModeUseCase: com.mysketch.shared.domain.usecase.chat.GetDisappearingModeUseCase,
    val getChatSettingsUseCase: com.mysketch.shared.domain.usecase.chat.GetChatSettingsUseCase,
    val getConversationMuteStatusUseCase: com.mysketch.shared.domain.usecase.chat.GetConversationMuteStatusUseCase,
    val setConversationMuteStatusUseCase: com.mysketch.shared.domain.usecase.chat.SetConversationMuteStatusUseCase,
    val chatLockManager: com.mysketch.core.util.ChatLockManager,
    val deleteExpiredMessagesUseCase: com.mysketch.shared.domain.usecase.chat.DeleteExpiredMessagesUseCase,
    /** Chat folders live in user settings; the header assigns the open chat to one of them. */
    val settingsRepository: com.mysketch.data.repository.SettingsRepository,
)

/**
 * Consolidated dependency configuration for ChatViewModel.
 * Groups related dependencies into configuration objects to reduce constructor parameter count.
 */
data class ChatDependencies(
    val messaging: ChatMessagingDependencies,
    val subscription: ChatSubscriptionDependencies,
    val ai: ChatAiDependencies,
    val media: ChatMediaDependencies,
    val settings: ChatSettingsDependencies,
    val initializeE2EUseCase: InitializeE2EUseCase,
    val getOrCreateChatUseCase: com.mysketch.shared.domain.usecase.chat.GetOrCreateChatUseCase,
    val getChatInfoUseCase: com.mysketch.shared.domain.usecase.chat.GetChatInfoUseCase,
    val getGroupMembersUseCase: com.mysketch.shared.domain.usecase.chat.GetGroupMembersUseCase,
    val getMessageByIdUseCase: com.mysketch.shared.domain.usecase.chat.GetMessageByIdUseCase,
    val toggleMessageReactionUseCase: ToggleMessageReactionUseCase,
    val observeUserActiveStatusUseCase: ObserveUserActiveStatusUseCase,
    val observeUserPresenceDetailUseCase: ObserveUserPresenceDetailUseCase,
    val updateCurrentChatUseCase: UpdateCurrentChatUseCase,
    val voteInPollUseCase: VoteInPollUseCase,
    val markViewOnceConsumedUseCase: MarkViewOnceConsumedUseCase,
    val searchMessagesUseCase: SearchMessagesUseCase,
    val exportChatUseCase: ExportChatUseCase,
    val reportChatUseCase: ReportChatUseCase,
    val clearChatHistoryUseCase: ClearChatHistoryUseCase,
    val blockUserUseCase: BlockUserUseCase,
    val reportUserUseCase: ReportUserUseCase,
)