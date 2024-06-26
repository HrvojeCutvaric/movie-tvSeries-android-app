package com.example.movieandroidapp.presentation.movies

import com.example.movieandroidapp.domain.models.movie.Movie

data class MoviesState (
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val nowPlayingMovieListPage: Int = 1,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
    val nowPlayingMovieList: List<Movie> = emptyList()
)