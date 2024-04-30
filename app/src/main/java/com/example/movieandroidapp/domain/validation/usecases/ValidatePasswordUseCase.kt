package com.example.movieandroidapp.domain.validation.usecases

import com.example.movieandroidapp.R
import com.example.movieandroidapp.domain.validation.ValidationResult
import com.example.movieandroidapp.presentation.utils.UiText

class ValidatePasswordUseCase {
    fun execute(input: String): ValidationResult {
        if(input.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.thePasswordNotLongEnough)
            )
        }
        if(!isPasswordValid(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(resId = R.string.invalidPassword)
            )
        }
        return ValidationResult(
            successful = true
        )
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.any { it.isDigit() } && password.any { it.isLetter() }
    }
}