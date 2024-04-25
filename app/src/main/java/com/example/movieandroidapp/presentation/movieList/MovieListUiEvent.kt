package com.example.movieandroidapp.presentation.movieList

sealed interface MovieListUiEvent {

    data class Paginate(val category: String) : MovieListUiEvent

}