package com.example.movieandroidapp.domain.utils

sealed class MoviesFragments(val rout: String) {
    object Popular: MoviesFragments(Category.POPULAR)
    object Upcoming: MoviesFragments(Category.UPCOMING)
    object Playing: MoviesFragments(Category.PLAYING)
}