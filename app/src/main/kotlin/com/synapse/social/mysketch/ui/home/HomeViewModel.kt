package com.mysketch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysketch.data.repository.SettingsRepository
import com.mysketch.shared.domain.usecase.user.GetCurrentUserAvatarUseCase
import com.mysketch.ui.settings.NavigationPlacement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserAvatarUseCase: GetCurrentUserAvatarUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val navigationPlacement: StateFlow<NavigationPlacement> = settingsRepository.navigationPlacement
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = com.mysketch.data.local.database.settings.SettingsConstants.DEFAULT_NAVIGATION_PLACEMENT
        )

    private val _userAvatarUrl = MutableStateFlow<String?>(null)
    val userAvatarUrl: StateFlow<String?> = _userAvatarUrl.asStateFlow()

    init {
        loadUserAvatar()
    }

    private fun loadUserAvatar() {
        viewModelScope.launch {
            getCurrentUserAvatarUseCase().onSuccess { avatar ->
                _userAvatarUrl.value = avatar
            }
        }
    }
}
