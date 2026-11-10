package com.mysketch.core.di

import com.mysketch.shared.data.datasource.SupabaseBlockDataSource
import com.mysketch.shared.data.repository.BlockRepositoryImpl
import com.mysketch.shared.domain.repository.BlockRepository
import com.mysketch.shared.domain.usecase.auth.GetCurrentUserIdUseCase
import com.mysketch.shared.domain.usecase.blocking.BlockUserUseCase
import com.mysketch.shared.domain.usecase.blocking.GetBlockedUsersUseCase
import com.mysketch.shared.domain.usecase.blocking.IsUserBlockedUseCase
import com.mysketch.shared.domain.usecase.blocking.UnblockUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlockingModule {

    @Provides
    @Singleton
    fun provideSupabaseBlockDataSource(client: SupabaseClient): SupabaseBlockDataSource {
        return SupabaseBlockDataSource(client)
    }

    @Provides
    @Singleton
    fun provideBlockUserUseCase(
        blockRepository: BlockRepository,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase
    ): BlockUserUseCase {
        return BlockUserUseCase(blockRepository, getCurrentUserIdUseCase)
    }

    @Provides
    @Singleton
    fun provideUnblockUserUseCase(blockRepository: BlockRepository): UnblockUserUseCase {
        return UnblockUserUseCase(blockRepository)
    }

    @Provides
    @Singleton
    fun provideGetBlockedUsersUseCase(blockRepository: BlockRepository): GetBlockedUsersUseCase {
        return GetBlockedUsersUseCase(blockRepository)
    }

    @Provides
    @Singleton
    fun provideIsUserBlockedUseCase(blockRepository: BlockRepository): IsUserBlockedUseCase {
        return IsUserBlockedUseCase(blockRepository)
    }
}


