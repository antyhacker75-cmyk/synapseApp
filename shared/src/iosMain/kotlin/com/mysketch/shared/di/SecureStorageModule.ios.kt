package com.mysketch.shared.di

import com.mysketch.shared.data.local.IosSecureStorage
import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.security.IosSecurityCipher
import com.mysketch.shared.security.SecurityCipher
import org.koin.dsl.module
import org.koin.core.module.Module

actual val secureStorageModule = module {
    single<SecureStorage> { IosSecureStorage() }
    single<SecurityCipher> { IosSecurityCipher() }
}
