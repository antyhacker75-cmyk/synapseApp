package com.mysketch.core.di

import android.content.Context
import com.mysketch.data.echo.EchoForegroundServiceHold
import com.mysketch.data.echo.EchoMediaHold
import com.mysketch.data.echo.audio.LiveKitEchoAudioEngine
import com.mysketch.shared.data.datasource.EchoRoomRealtimeDataSource
import com.mysketch.shared.data.repository.SupabaseEchoRoomRepository
import com.mysketch.shared.data.repository.SupabaseEchoTokenRepository
import com.mysketch.shared.domain.repository.EchoRoomRepository
import com.mysketch.shared.domain.repository.EchoTokenRepository
import com.mysketch.shared.domain.service.EchoAudioEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient as SupabaseClientType
import javax.inject.Singleton

/**
 * Backend half of Echo Rooms.
 *
 * The repository is stateless — every read is an RPC and every change is announced through the
 * realtime data source — but the data source is a singleton so a room screen that recomposes does
 * not open a second channel to the same room.
 */
@Module
@InstallIn(SingletonComponent::class)
object EchoRoomModule {

    @Provides
    @Singleton
    fun provideEchoRoomRealtimeDataSource(
        client: SupabaseClientType
    ): EchoRoomRealtimeDataSource = EchoRoomRealtimeDataSource(client)

    @Provides
    @Singleton
    fun provideEchoRoomRepository(
        client: SupabaseClientType,
        realtime: EchoRoomRealtimeDataSource
    ): EchoRoomRepository = SupabaseEchoRoomRepository(client, realtime)

    @Provides
    @Singleton
    fun provideEchoTokenRepository(client: SupabaseClientType): EchoTokenRepository =
        SupabaseEchoTokenRepository(client)

    @Provides
    @Singleton
    fun provideEchoAudioEngine(engine: LiveKitEchoAudioEngine): EchoAudioEngine = engine

    @Provides
    @Singleton
    fun provideEchoMediaHold(@ApplicationContext context: Context): EchoMediaHold =
        EchoForegroundServiceHold(context)
}
