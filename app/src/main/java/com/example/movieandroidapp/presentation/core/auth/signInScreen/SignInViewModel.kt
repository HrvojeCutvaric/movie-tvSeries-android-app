package com.example.movieandroidapp.presentation.core.auth.signInScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movieandroidapp.domain.validation.usecases.ValidateEmailUseCase
import com.example.movieandroidapp.domain.validation.usecases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SignInViewModel : ViewModel() {

    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(SignInState())

    fun onEvent(event: SignInEvent): Boolean {

        var isUserInputValid = false

        when (event) {
            is SignInEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                validateEmail()
            }

            is SignInEvent.PasswordChange -> {
                formState = formState.copy(password = event.password)
                validatePassword()
            }

            SignInEvent.Submit -> {
                isUserInputValid = validateEmail() && validatePassword()
            }
        }

        return event == SignInEvent.Submit && isUserInputValid
    }

    private fun validateEmail(): Boolean {
        val emailResult = validateEmailUseCase.execute(formState.email)
        formState = formState.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = validatePasswordUseCase.execute(formState.password)
        formState = formState.copy(passwordError = passwordResult.errorMessage)
        return passwordResult.successful
    }

}