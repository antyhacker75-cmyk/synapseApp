package com.mysketch.shared.domain.usecase.search

import com.mysketch.shared.domain.model.SearchPost
import com.mysketch.shared.domain.repository.ISearchRepository

class SearchPostsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(query: String): Result<List<SearchPost>> {
        return repository.searchPosts(query)
    }
}
