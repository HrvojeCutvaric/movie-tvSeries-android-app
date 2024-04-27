package com.example.movieandroidapp.presentation.movies

sealed interface MoviesUiEvent {

    data class Paginate(val category: String) : MoviesUiEvent

}