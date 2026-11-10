package com.mysketch.shared.di

import com.mysketch.shared.data.repository.SupabasePresenceRepository
import com.mysketch.shared.domain.repository.PresenceRepository
import com.mysketch.shared.domain.usecase.presence.ObserveUserActiveStatusUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserPresenceDetailUseCase
import com.mysketch.shared.domain.usecase.presence.ObserveUserPresenceUseCase
import com.mysketch.shared.domain.usecase.presence.StartPresenceTrackingUseCase
import com.mysketch.shared.domain.usecase.presence.StopPresenceTrackingUseCase
import com.mysketch.shared.domain.usecase.presence.UpdateCurrentChatUseCase
import com.mysketch.shared.domain.usecase.presence.UpdatePresenceUseCase
import org.koin.dsl.module

val presenceModule = module {
    single<PresenceRepository> { SupabasePresenceRepository() }
    single { UpdatePresenceUseCase(get()) }
    single { StartPresenceTrackingUseCase(get()) }
    single { StopPresenceTrackingUseCase(get()) }
    single { ObserveUserPresenceUseCase(get()) }
    single { ObserveUserActiveStatusUseCase(get()) }
    single { ObserveUserPresenceDetailUseCase(get()) }
    single { UpdateCurrentChatUseCase(get()) }
}
