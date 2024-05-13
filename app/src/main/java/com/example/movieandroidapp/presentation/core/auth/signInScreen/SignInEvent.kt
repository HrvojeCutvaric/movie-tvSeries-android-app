package com.example.movieandroidapp.presentation.core.auth.signInScreen

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChange(val password: String) : SignInEvent()
    object Submit : SignInEvent()
}