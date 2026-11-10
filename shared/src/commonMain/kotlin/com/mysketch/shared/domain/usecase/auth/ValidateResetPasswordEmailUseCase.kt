package com.mysketch.shared.domain.usecase.auth

import com.mysketch.shared.domain.model.ValidationResult

class ValidateResetPasswordEmailUseCase(
    private val validateEmailUseCase: ValidateEmailUseCase
) {
    data class ValidationResultGroup(
        val isValid: Boolean,
        val emailError: String? = null
    )

    operator fun invoke(email: String): ValidationResultGroup {
        val emailValidation = validateEmailUseCase(email)
        val emailError = (emailValidation as? ValidationResult.Invalid)?.errorMessage

        return ValidationResultGroup(
            isValid = emailError == null,
            emailError = emailError
        )
    }
}
