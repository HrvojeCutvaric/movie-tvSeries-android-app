package com.example.movieandroidapp.domain.validation.usecases

import com.example.movieandroidapp.R
import com.example.movieandroidapp.domain.validation.ValidationResult
import com.example.movieandroidapp.presentation.utils.UiText

class ValidateConfirmPasswordUseCase {
    fun execute(input: String, password: String): ValidationResult {
        if(input != password) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalidConfirmPassword)
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}