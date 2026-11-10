package com.mysketch.shared.domain.usecase.search

import com.mysketch.shared.domain.model.SearchAccount
import com.mysketch.shared.domain.repository.ISearchRepository

class GetSuggestedAccountsUseCase(private val repository: ISearchRepository) {
    suspend operator fun invoke(query: String): Result<List<SearchAccount>> {
        return repository.getSuggestedAccounts(query)
    }
}
