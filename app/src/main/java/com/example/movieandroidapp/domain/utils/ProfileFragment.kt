package com.example.movieandroidapp.domain.utils

sealed class ProfileFragment(val rout: String) {
    data object AuthorizedUser : ProfileFragment("authorized")
    data object UnauthorizedUser : ProfileFragment("unauthorized")
}