package com.example.movieandroidapp.presentation.movieList

import com.example.movieandroidapp.domain.models.Movie

data class MovieListState (
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,

    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)