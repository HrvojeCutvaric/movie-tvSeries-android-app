package com.example.movieandroidapp.presentation.core.auth.signUpScreen

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChange(val password: String) : SignUpEvent()
    data class ConfirmPasswordChange(val confirmPassword: String) : SignUpEvent()
    object Submit : SignUpEvent()
}