package com.mysketch.core.di

import com.mysketch.data.call.webrtc.CallMediaHost
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Video surfaces are views rather than ViewModels, so they reach the object graph the same way
 * workers do — through an application entry point instead of `hiltViewModel()`.
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface CallMediaEntryPoint {
    fun callMediaHost(): CallMediaHost
}
