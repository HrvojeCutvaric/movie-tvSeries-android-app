package com.example.movieandroidapp.domain.repositories

import com.example.movieandroidapp.data.remote.firebase.AuthResource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun signIn(email: String, password: String): AuthResource<FirebaseUser>
    suspend fun signUp(email: String, password: String): AuthResource<FirebaseUser>
    fun logOut()
}