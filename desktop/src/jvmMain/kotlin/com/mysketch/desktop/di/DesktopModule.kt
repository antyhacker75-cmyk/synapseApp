package com.mysketch.desktop.di

import com.mysketch.desktop.ui.DesktopAuthViewModel
import com.mysketch.desktop.ui.DesktopChatViewModel
import com.mysketch.shared.domain.repository.MediaUploadRepository
import com.mysketch.shared.data.datasource.SupabaseChatDataSource
import com.mysketch.shared.data.repository.SupabaseAuthRepository
import com.mysketch.shared.data.repository.SupabaseChatRepository
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.shared.domain.repository.ChatRepository
import com.mysketch.shared.domain.usecase.auth.SignInUseCase
import com.mysketch.shared.domain.usecase.chat.GetConversationsUseCase
import com.mysketch.shared.domain.usecase.chat.GetMessagesUseCase
import com.mysketch.shared.domain.usecase.chat.SendMessageUseCase
import com.mysketch.shared.domain.usecase.auth.GetCurrentUserIdUseCase
import com.mysketch.shared.core.network.SupabaseClient
import org.koin.dsl.module

val desktopModule = module {
    // Auth
    single<AuthRepository> { SupabaseAuthRepository(SupabaseClient.client) }
    single { SignInUseCase(get()) }
    single { GetCurrentUserIdUseCase(get()) }
    factory { DesktopAuthViewModel(get()) }

    // Media upload (no-op stub — desktop does not support media uploads)
    single<MediaUploadRepository> { DesktopMediaUploadRepository() }

    // Chat
    single { SupabaseChatDataSource(SupabaseClient.client) }

    single<ChatRepository> {
        SupabaseChatRepository(
            dataSource = get(),
            client = SupabaseClient.client,
            signalProtocolManager = null,
            mediaUploadRepository = get<MediaUploadRepository>(),
            presenceRepository = getOrNull(),
            offlineActionRepository = getOrNull(),
            cachedMessageDao = getOrNull(),
            cachedConversationDao = getOrNull()
        )
    }

    single { GetConversationsUseCase(get()) }
    single { GetMessagesUseCase(get()) }
    single { SendMessageUseCase(get(), null) }

    factory { DesktopChatViewModel(get(), get(), get(), get()) }
}
