package com.mysketch.shared.di

import com.mysketch.shared.data.local.AndroidSecureStorage
import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.security.AndroidSecurityCipher
import com.mysketch.shared.security.SecurityCipher
import org.koin.dsl.module
import org.koin.core.module.Module

actual val secureStorageModule = module {
    single<SecureStorage> { AndroidSecureStorage(get()) }
    single<SecurityCipher> { AndroidSecurityCipher() }
}
