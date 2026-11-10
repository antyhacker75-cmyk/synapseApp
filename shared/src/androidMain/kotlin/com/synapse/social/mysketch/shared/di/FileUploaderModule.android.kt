package com.mysketch.shared.di

import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import com.mysketch.shared.domain.repository.FileUploader
import org.koin.core.module.Module
import com.mysketch.shared.core.media.AndroidMediaCompressor
import com.mysketch.shared.domain.service.MediaCompressor
import org.koin.dsl.bind

actual val fileUploaderModule: Module = module {
    single { FileUploader(androidContext()) }
    single { AndroidMediaCompressor(androidContext()) } bind MediaCompressor::class
}
