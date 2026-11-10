package com.mysketch.shared.domain.usecase.search

import com.mysketch.shared.domain.model.SearchNews
import com.mysketch.shared.domain.repository.ISearchRepository

class SearchNewsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(query: String): Result<List<SearchNews>> {
        return repository.searchNews(query)
    }
}
