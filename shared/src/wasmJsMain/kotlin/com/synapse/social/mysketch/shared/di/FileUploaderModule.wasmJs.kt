package com.mysketch.shared.di

import org.koin.dsl.module
import com.mysketch.shared.domain.repository.FileUploader
import com.mysketch.shared.domain.service.MediaCompressor
import com.mysketch.shared.core.media.WasmJsMediaCompressor
import org.koin.core.module.Module
import org.koin.dsl.bind

actual val fileUploaderModule: Module = module {
    single { FileUploader() }
    single { WasmJsMediaCompressor() } bind MediaCompressor::class
}
