package com.example.movieandroidapp.domain.utils

sealed class Screen(val rout: String) {
    object Main: Screen("main")
    object MovieDetails: Screen("movie_details")
    object SignUp: Screen("sign_up")
    object Home: Screen("home")
    object Movies: Screen("movies")
    object TvSeries: Screen("tv_series")
    object Profile: Screen("profile")
}