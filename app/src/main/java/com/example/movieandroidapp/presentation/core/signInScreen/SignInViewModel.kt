package com.example.movieandroidapp.presentation.core.signInScreen

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandroidapp.data.remote.firebase.AuthResource
import com.example.movieandroidapp.domain.repositories.AuthRepository
import com.example.movieandroidapp.domain.utils.Resource
import com.example.movieandroidapp.domain.validation.usecases.ValidateEmailUseCase
import com.example.movieandroidapp.domain.validation.usecases.ValidatePasswordUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val validateEmailUseCase = ValidateEmailUseCase()
    private val validatePasswordUseCase = ValidatePasswordUseCase()

    var formState by mutableStateOf(SignInState())

    private val _singInFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
    val signInFlow: StateFlow<AuthResource<FirebaseUser>?> = _singInFlow

    fun onEvent(event: SignInEvent) {
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
                if (validateEmail() && validatePassword()) {
                    signIn(formState.email, formState.password)
                } else {
                    Log.e("SIGN_IN", "Failed to Sign In")
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

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    init {
        if (authRepository.currentUser != null) {
            _singInFlow.value = AuthResource.Success(authRepository.currentUser!!)
        }
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _singInFlow.value = AuthResource.Loading
        val result = authRepository.signIn(email, password)
        _singInFlow.value = result
    }

    fun logout() {
        authRepository.logOut()
        _singInFlow.value
    }

}