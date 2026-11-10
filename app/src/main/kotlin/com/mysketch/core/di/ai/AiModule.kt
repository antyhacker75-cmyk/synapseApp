package com.mysketch.core.di.ai

import com.mysketch.data.repository.ai.AiRepositoryImpl
import com.mysketch.data.repository.ai.MultiProviderAiRepository
import com.mysketch.domain.repository.ai.AiRepository
import com.mysketch.shared.domain.usecase.ai.GenerateSmartRepliesUseCase
import com.mysketch.domain.usecase.ai.SummarizeChatUseCase
import com.mysketch.domain.usecase.ai.SummarizeMessageUseCase
import com.mysketch.domain.usecase.ai.SummarizePostUseCase
import com.mysketch.domain.usecase.ai.SummarizeThreadUseCase
import com.mysketch.settings.ApiKeySettingsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AiModule {

    @Provides
    @Singleton
    fun provideSharedAiRepository(
        apiKeySettingsService: ApiKeySettingsService
    ): com.mysketch.shared.domain.repository.ai.AiRepository {
        val platformRepository = AiRepositoryImpl(apiKeySettingsService)
        return MultiProviderAiRepository(platformRepository, apiKeySettingsService)
    }

    @Provides
    @Singleton
    fun provideAiRepository(
        sharedAiRepository: com.mysketch.shared.domain.repository.ai.AiRepository
    ): AiRepository {
        return (sharedAiRepository as? AiRepository) ?: throw IllegalStateException("The provided sharedAiRepository does not implement the platform AiRepository interface.")
    }

    @Provides
    @Singleton
    fun provideGenerateSmartRepliesUseCase(
        aiRepository: com.mysketch.shared.domain.repository.ai.AiRepository
    ): GenerateSmartRepliesUseCase {
        return GenerateSmartRepliesUseCase(aiRepository)
    }

    @Provides
    @Singleton
    fun provideSummarizeChatUseCase(aiRepository: AiRepository): SummarizeChatUseCase {
        return SummarizeChatUseCase(aiRepository)
    }

    @Provides
    @Singleton
    fun provideSummarizePostUseCase(aiRepository: AiRepository): SummarizePostUseCase {
        return SummarizePostUseCase(aiRepository)
    }

    @Provides
    @Singleton
    fun provideSummarizeMessageUseCase(aiRepository: AiRepository): SummarizeMessageUseCase {
        return SummarizeMessageUseCase(aiRepository)
    }

    @Provides
    @Singleton
    fun provideSummarizeThreadUseCase(aiRepository: AiRepository): SummarizeThreadUseCase {
        return SummarizeThreadUseCase(aiRepository)
    }
}
