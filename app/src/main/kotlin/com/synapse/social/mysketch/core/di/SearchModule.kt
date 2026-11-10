package com.mysketch.core.di

import com.mysketch.shared.data.repository.SearchRepositoryImpl
import com.mysketch.shared.domain.repository.ISearchRepository
import com.mysketch.shared.domain.usecase.search.GetPostsByHashtagUseCase
import com.mysketch.shared.domain.usecase.search.GetSuggestedAccountsUseCase
import com.mysketch.shared.domain.usecase.search.GetTrendingHashtagsUseCase
import com.mysketch.shared.domain.usecase.search.SearchHashtagsUseCase
import com.mysketch.shared.domain.usecase.search.SearchNewsUseCase
import com.mysketch.shared.domain.usecase.search.SearchPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchPostsUseCase(repository: ISearchRepository): SearchPostsUseCase {
        return SearchPostsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchHashtagsUseCase(repository: ISearchRepository): SearchHashtagsUseCase {
        return SearchHashtagsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchNewsUseCase(repository: ISearchRepository): SearchNewsUseCase {
        return SearchNewsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSuggestedAccountsUseCase(repository: ISearchRepository): GetSuggestedAccountsUseCase {
        return GetSuggestedAccountsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTrendingHashtagsUseCase(repository: ISearchRepository): GetTrendingHashtagsUseCase {
        return GetTrendingHashtagsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPostsByHashtagUseCase(repository: ISearchRepository): GetPostsByHashtagUseCase {
        return GetPostsByHashtagUseCase(repository)
    }
}

