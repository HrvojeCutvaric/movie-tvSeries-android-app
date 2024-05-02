package com.example.movieandroidapp.data.repositories

import com.example.movieandroidapp.data.remote.firebase.AuthResource
import com.example.movieandroidapp.domain.repositories.AuthRepository
import com.example.movieandroidapp.domain.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): AuthResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResource.Failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): AuthResource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResource.Failure(e)
        }
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }
}