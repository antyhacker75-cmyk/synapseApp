package com.mysketch.core.di

import com.mysketch.data.call.AndroidCallPermissionRepository
import com.mysketch.data.call.CallForegroundServiceHold
import com.mysketch.data.call.CallMediaHold
import com.mysketch.data.call.audio.AndroidCallAudioRouter
import com.mysketch.data.call.audio.CallAudioRouter
import com.mysketch.shared.domain.repository.CallPermissionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * What a call needs from the device itself: what the audio hardware is doing, what the user has
 * granted, and whether the process may keep the microphone open when its screen is not on top.
 *
 * These live apart from the repository modules because those are split per build type
 * (`ReleaseCallModule` in `app/src/release`, `DebugCallModule` in `app/src/debug`) while this one is
 * wanted either way. A real call and a simulated one both run on the same audio device, and the
 * permission state a simulated call reports is the permission state the next real call will read.
 */
@Module
@InstallIn(SingletonComponent::class)
object CallDeviceModule {

    @Provides
    @Singleton
    fun provideCallAudioRouter(router: AndroidCallAudioRouter): CallAudioRouter = router

    @Provides
    @Singleton
    fun provideCallPermissionRepository(repository: AndroidCallPermissionRepository): CallPermissionRepository =
        repository

    /**
     * The licence to hold a microphone open with the screen off.
     *
     * Bound here rather than in the repository modules because it is a device capability, and because the
     * debug build's real repository needs it exactly as much as the release one does — only a simulated
     * call never acquires it, and that repository is not injected with it at all.
     */
    @Provides
    @Singleton
    fun provideCallMediaHold(hold: CallForegroundServiceHold): CallMediaHold = hold
}
