package com.example.movieandroidapp.presentation.core.auth.signUpScreen

import com.example.movieandroidapp.presentation.utils.UiText

data class SignUpState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: UiText? = null
)
