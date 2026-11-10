package com.mysketch.shared.di

import com.mysketch.shared.data.passkey.AndroidPasskeyPlatformManager
import com.mysketch.shared.data.passkey.PasskeyActivityProvider
import com.mysketch.shared.domain.repository.PasskeyPlatformManager
import org.koin.dsl.module

actual val passkeyPlatformModule = module {
    single { PasskeyActivityProvider() }
    single<PasskeyPlatformManager> { AndroidPasskeyPlatformManager(get()) }
}
