package com.mysketch.domain.usecase.user

import com.mysketch.data.repository.UserRepositoryImpl
import com.mysketch.domain.model.User
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepositoryImpl
) {
    suspend operator fun invoke(userId: String): Result<User?> {
        return repository.getUserById(userId)
    }
}
