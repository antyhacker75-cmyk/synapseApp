package com.mysketch.shared.di

import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.data.repository.PasskeyRepositoryImpl
import com.mysketch.shared.domain.repository.PasskeyRepository
import com.mysketch.shared.domain.usecase.auth.AddPasskeyUseCase
import com.mysketch.shared.domain.usecase.auth.LoadPasskeysUseCase
import com.mysketch.shared.domain.usecase.auth.RemovePasskeyUseCase
import com.mysketch.shared.domain.usecase.auth.SignInWithPasskeyUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

/** Platform implementations register [PasskeyPlatformManager] here. */
expect val passkeyPlatformModule: Module

val passkeyModule = module {
    includes(passkeyPlatformModule)

    single<PasskeyRepository> { PasskeyRepositoryImpl(SupabaseClient.client) }
    single { LoadPasskeysUseCase(get()) }
    single { RemovePasskeyUseCase(get()) }
    single { AddPasskeyUseCase(get(), get()) }
    single { SignInWithPasskeyUseCase(get(), get(), get()) }
}
