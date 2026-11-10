package com.mysketch.shared.di

import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.data.local.JvmSecureStorage
import com.mysketch.shared.security.SecurityCipher
import com.mysketch.shared.security.JvmSecurityCipher
import org.koin.dsl.module
import org.koin.core.module.Module

actual val secureStorageModule = module {
    single<SecureStorage> { JvmSecureStorage() }
    single<SecurityCipher> { JvmSecurityCipher() }
}
