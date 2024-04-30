package com.example.movieandroidapp.presentation.core.signUpScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movieandroidapp.domain.validation.usecases.ValidateConfirmPasswordUseCase
import com.example.movieandroidapp.domain.validation.usecases.ValidateEmailUseCase
import com.example.movieandroidapp.domain.validation.usecases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()
    private val validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase()

    var formState by mutableStateOf(SignUpState())

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
                validateEmail()
            }

            is SignUpEvent.PasswordChange -> {
                formState = formState.copy(password = event.password)
                validatePassword()
            }

            is SignUpEvent.ConfirmPasswordChange -> {
                formState = formState.copy(confirmPassword = event.confirmPassword)
                validateConfirmPassword()
            }
            SignUpEvent.Submit -> {
                if (validateEmail() && validatePassword() && validateConfirmPassword()){
                    Log.i("SIGN_UP","Successful Sign Up")
                }else {
                    Log.e("SIGN_UP", "Failed to Sign Up")
                }
            }
        }
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

    private fun validateConfirmPassword(): Boolean {
        val confirmPasswordResult = validateConfirmPasswordUseCase.execute(formState.confirmPassword, formState.password)
        formState = formState.copy(confirmPasswordError = confirmPasswordResult.errorMessage)
        return confirmPasswordResult.successful
    }


}