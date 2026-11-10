package com.mysketch.shared.di

import com.mysketch.shared.data.local.SecureStorage
import com.mysketch.shared.data.local.WasmJsSecureStorage
import com.mysketch.shared.security.SecurityCipher
import com.mysketch.shared.security.WasmJsSecurityCipher
import org.koin.dsl.module
import org.koin.core.module.Module

actual val secureStorageModule = module {
    single<SecurityCipher> { WasmJsSecurityCipher() }
    single<SecureStorage> { WasmJsSecureStorage(get()) }
}
