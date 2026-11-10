package com.mysketch.shared.domain.usecase.user

import com.mysketch.shared.domain.model.User
import com.mysketch.shared.domain.repository.UserRepository

class SearchUsersUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(query: String): Result<List<User>> {
        return repository.searchUsers(query)
    }
}
