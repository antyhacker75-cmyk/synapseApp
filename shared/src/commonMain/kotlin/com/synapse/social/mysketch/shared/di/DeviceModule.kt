package com.mysketch.shared.di

import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.data.datasource.ChatParticipantSettingsDataSource
import com.mysketch.shared.data.datasource.DeviceRegistrationDataSource
import com.mysketch.shared.domain.usecase.device.RegisterDeviceUseCase
import com.mysketch.shared.domain.usecase.device.UpdateDevicePresenceUseCase
import org.koin.dsl.module

val deviceModule = module {
    single { DeviceRegistrationDataSource(SupabaseClient.client) }
    single { ChatParticipantSettingsDataSource(SupabaseClient.client) }
    
    single { RegisterDeviceUseCase(get()) }
    single { UpdateDevicePresenceUseCase(get()) }
}
