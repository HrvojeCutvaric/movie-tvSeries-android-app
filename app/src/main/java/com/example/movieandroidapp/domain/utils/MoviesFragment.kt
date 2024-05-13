package com.example.movieandroidapp.domain.utils

sealed class MoviesFragment(val rout: String) {
    object Popular: MoviesFragment(Category.POPULAR)
    object Upcoming: MoviesFragment(Category.UPCOMING)
    object Playing: MoviesFragment(Category.PLAYING)
}