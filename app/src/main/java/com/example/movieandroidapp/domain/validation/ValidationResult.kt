package com.example.movieandroidapp.domain.validation

import com.example.movieandroidapp.presentation.utils.UiText

data class ValidationResult (
    val successful: Boolean,
    val errorMessage: UiText? = null
)