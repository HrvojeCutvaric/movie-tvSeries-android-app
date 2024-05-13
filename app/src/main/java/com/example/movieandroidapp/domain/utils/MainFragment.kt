package com.example.movieandroidapp.domain.utils

sealed class MainFragment(val rout: String) {
    object Home: MainFragment("home")
    object Movies: MainFragment("movies")
    object TvSeries: MainFragment("tv_series")
    object Profile: MainFragment("profile")
}