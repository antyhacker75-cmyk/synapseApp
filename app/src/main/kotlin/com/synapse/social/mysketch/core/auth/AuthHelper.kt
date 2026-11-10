package com.mysketch.core.auth

import com.mysketch.shared.data.repository.SupabaseAuthRepository
import com.mysketch.shared.domain.usecase.auth.GetCurrentUserIdUseCase
import com.mysketch.shared.domain.usecase.auth.GetCurrentUserEmailUseCase

object AuthHelper {
    private val authRepository by lazy { SupabaseAuthRepository() }
    private val getCurrentUserIdUseCase by lazy {
        GetCurrentUserIdUseCase(authRepository)
    }
    
    private val getCurrentUserEmailUseCase by lazy {
        GetCurrentUserEmailUseCase(authRepository)
    }
    
    fun getCurrentUserId(): String? = getCurrentUserIdUseCase()
    
    fun getCurrentUserEmail(): String? = getCurrentUserEmailUseCase()
}
