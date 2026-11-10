package com.mysketch.di

import com.mysketch.data.repository.AssetOpenSourceLicenseRepository
import com.mysketch.data.repository.FeedbackRepositoryImpl
import com.mysketch.data.repository.GitHubAppUpdateRepository
import com.mysketch.data.repository.GitHubReleaseRepository
import com.mysketch.domain.repository.AppReleaseRepository
import com.mysketch.domain.repository.AppUpdateRepository
import com.mysketch.domain.repository.FeedbackRepository
import com.mysketch.domain.repository.OpenSourceLicenseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AboutSupportRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFeedbackRepository(implementation: FeedbackRepositoryImpl): FeedbackRepository

    @Binds
    @Singleton
    abstract fun bindAppUpdateRepository(implementation: GitHubAppUpdateRepository): AppUpdateRepository

    @Binds
    @Singleton
    abstract fun bindOpenSourceLicenseRepository(
        implementation: AssetOpenSourceLicenseRepository
    ): OpenSourceLicenseRepository

    @Binds
    @Singleton
    abstract fun bindAppReleaseRepository(implementation: GitHubReleaseRepository): AppReleaseRepository
}
