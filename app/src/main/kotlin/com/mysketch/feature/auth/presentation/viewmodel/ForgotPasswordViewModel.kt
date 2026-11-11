package com.mysketch.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysketch.feature.auth.ui.models.AuthNavigationEvent
import com.mysketch.feature.auth.ui.models.AuthUiState
import com.mysketch.feature.auth.ui.models.AuthField
import com.mysketch.shared.domain.model.ValidationResult
import com.mysketch.shared.domain.usecase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateResetPasswordEmailUseCase: ValidateResetPasswordEmailUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.ForgotPassword())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<AuthNavigationEvent>()
    val navigationEvent: SharedFlow<AuthNavigationEvent> = _navigationEvent.asSharedFlow()

    fun onEmailChanged(email: String) {
        val isValid = validateEmailUseCase(email) is ValidationResult.Valid
        _uiState.value = AuthInputHelper.handleEmailChanged(_uiState.value, email, isValid)
    }

    fun onSubmitNewPassword() {
         val state = _uiState.value as? AuthUiState.ForgotPassword ?: return
         val validation = validateResetPasswordEmailUseCase(state.email)

         if (!validation.isValid) {
             _uiState.value = state.copy(validationErrors = mapOf(AuthField.EMAIL to validation.emailError))
             return
         }

         viewModelScope.launch {
             _uiState.value = state.copy(isLoading = true, generalError = null)
             sendPasswordResetUseCase(state.email).fold(
                 onSuccess = {
                     _uiState.value = state.copy(isLoading = false, isEmailSent = true, generalError = null)
                 },
                 onFailure = { error ->
                     val message = when ((error as? com.google.firebase.auth.FirebaseAuthException)?.errorCode) {
                         "ERROR_INVALID_EMAIL" -> "That email address is not valid."
                         "ERROR_USER_NOT_FOUND" -> "No account was found for that email address."
                         "ERROR_TOO_MANY_REQUESTS" -> "Too many reset requests. Please wait and try again."
                         "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Check your internet connection and try again."
                         else -> error.message ?: "Firebase could not send the password reset email. Check your connection and Firebase email settings."
                     }
                     _uiState.value = state.copy(isLoading = false, generalError = message)
                 }
             )
         }
    }

    fun onDismissError() {
        val state = _uiState.value as? AuthUiState.ForgotPassword ?: return
        _uiState.value = state.copy(generalError = null)
    }

    fun onBackToSignInClick() {
        viewModelScope.launch { _navigationEvent.emit(AuthNavigationEvent.NavigateBack) }
    }
}
