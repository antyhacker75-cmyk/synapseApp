package com.mysketch.shared.di

import org.koin.dsl.module
import com.mysketch.shared.domain.repository.FileUploader
import org.koin.core.module.Module
import com.mysketch.shared.core.media.IosMediaCompressor
import com.mysketch.shared.domain.service.MediaCompressor
import org.koin.dsl.bind

actual val fileUploaderModule: Module = module {
    single { FileUploader() }
    single { IosMediaCompressor() } bind MediaCompressor::class
}
