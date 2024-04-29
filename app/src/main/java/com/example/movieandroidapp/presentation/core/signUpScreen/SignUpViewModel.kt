package com.example.movieandroidapp.presentation.core.signUpScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    var email by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        email = input
    }
    var password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        password = input
    }
    var confirmPassword by mutableStateOf("")
        private set

    fun updateConfirmPassword(input: String) {
        confirmPassword = input
    }



}