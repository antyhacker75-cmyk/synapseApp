package com.mysketch.shared.di

import com.mysketch.shared.data.passkey.UnsupportedPasskeyPlatformManager
import com.mysketch.shared.domain.repository.PasskeyPlatformManager
import org.koin.dsl.module

actual val passkeyPlatformModule = module {
    single<PasskeyPlatformManager> { UnsupportedPasskeyPlatformManager() }
}
