package com.example.movieandroidapp.presentation.core.movieDetails

import com.example.movieandroidapp.domain.models.movie.Movie
import com.example.movieandroidapp.domain.models.movie.details.MovieDetails

data class MovieDetailsState (
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val movieDetails: MovieDetails? = null
)