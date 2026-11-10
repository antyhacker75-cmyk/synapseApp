package com.mysketch.shared.domain.usecase.search

import com.mysketch.shared.domain.model.SearchHashtag
import com.mysketch.shared.domain.repository.ISearchRepository

class GetTrendingHashtagsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(): Result<List<SearchHashtag>> = repository.getTrendingHashtags()
}
