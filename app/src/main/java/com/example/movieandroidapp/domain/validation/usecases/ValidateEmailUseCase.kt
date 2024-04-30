package com.example.movieandroidapp.domain.validation.usecases

import android.util.Patterns
import com.example.movieandroidapp.R
import com.example.movieandroidapp.domain.validation.ValidationResult
import com.example.movieandroidapp.presentation.utils.UiText

class ValidateEmailUseCase {

    fun execute(input: String): ValidationResult {
        if(input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.theEmailCanNotBeBlank)
            )
        }
        if(!isEmailValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalidEmail)
            )
        }

        return ValidationResult(
            successful = true
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}