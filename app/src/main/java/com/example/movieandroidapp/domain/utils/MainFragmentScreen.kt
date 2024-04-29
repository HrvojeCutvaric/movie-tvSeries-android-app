package com.example.movieandroidapp.domain.utils

sealed class MainFragmentScreen(val rout: String) {
    object Home: MainFragmentScreen("home")
    object Movies: MainFragmentScreen("movies")
    object TvSeries: MainFragmentScreen("tv_series")
    object Profile: MainFragmentScreen("profile")
}