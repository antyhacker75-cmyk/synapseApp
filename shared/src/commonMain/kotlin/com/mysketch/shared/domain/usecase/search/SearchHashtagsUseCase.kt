package com.mysketch.shared.domain.usecase.search

import com.mysketch.shared.domain.model.SearchHashtag
import com.mysketch.shared.domain.repository.ISearchRepository

class SearchHashtagsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(query: String): Result<List<SearchHashtag>> {
        return if (query.isBlank()) {
            repository.getTrendingHashtags()
        } else {
            repository.searchHashtags(query)
        }
    }
}
