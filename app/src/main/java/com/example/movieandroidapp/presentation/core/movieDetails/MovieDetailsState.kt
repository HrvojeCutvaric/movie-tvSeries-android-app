package com.example.movieandroidapp.presentation.core.movieDetails

import com.example.movieandroidapp.domain.models.Movie

data class MovieDetailsState (
    val isLoading: Boolean = false,
    val movie: Movie? = null
)