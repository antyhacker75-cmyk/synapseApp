package com.mysketch.feature.profile.lockprofile.di

import com.mysketch.data.repository.ProfileActionRepositoryImpl
import com.mysketch.domain.repository.ProfilePrivacyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfilePrivacyModule {
    @Binds
    abstract fun bindProfilePrivacyRepository(implementation: ProfileActionRepositoryImpl): ProfilePrivacyRepository
}
