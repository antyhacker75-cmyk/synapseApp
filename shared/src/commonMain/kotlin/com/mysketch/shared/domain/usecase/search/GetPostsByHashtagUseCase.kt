package com.mysketch.shared.domain.usecase.search

import com.mysketch.shared.domain.model.SearchPost
import com.mysketch.shared.domain.repository.ISearchRepository

class GetPostsByHashtagUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(tag: String): Result<List<SearchPost>> = repository.getPostsByHashtag(tag)
}
