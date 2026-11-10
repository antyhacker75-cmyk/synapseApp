package com.mysketch.feature.shared.viewmodel

import androidx.lifecycle.ViewModel
import com.mysketch.shared.domain.usecase.presence.ObserveUserActiveStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserPresenceViewModel @Inject constructor(
    private val observeUserActiveStatusUseCase: ObserveUserActiveStatusUseCase
) : ViewModel() {
    
    fun observeUserStatus(userId: String): Flow<Boolean> {
        return observeUserActiveStatusUseCase(userId)
    }
}
