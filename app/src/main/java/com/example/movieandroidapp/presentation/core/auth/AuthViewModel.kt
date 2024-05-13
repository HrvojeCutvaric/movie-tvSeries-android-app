package com.example.movieandroidapp.presentation.core.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandroidapp.data.remote.firebase.AuthResource
import com.example.movieandroidapp.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
    val authState: StateFlow<AuthResource<FirebaseUser>?> = _authState

    init {
        if (authRepository.currentUser != null) {
            _authState.value = AuthResource.Success(authRepository.currentUser!!)
        }
    }

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser

    fun signUp(email: String, password: String) = viewModelScope.launch {
        _authState.value = AuthResource.Loading
        val result = authRepository.signUp(email, password)
        _authState.value = result
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _authState.value = AuthResource.Loading
        val result = authRepository.signIn(email, password)
        _authState.value = result
    }

    fun logout(){
        authRepository.logOut()
        _authState.value
    }

}