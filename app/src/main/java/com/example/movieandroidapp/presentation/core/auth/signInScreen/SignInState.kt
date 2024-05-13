package com.example.movieandroidapp.presentation.core.auth.signInScreen

import com.example.movieandroidapp.presentation.utils.UiText

data class SignInState(
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null
)
