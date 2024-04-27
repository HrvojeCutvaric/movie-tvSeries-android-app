package com.example.movieandroidapp.presentation.core.movieDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandroidapp.domain.repositories.MovieListRepository
import com.example.movieandroidapp.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val  savedStateHandle: SavedStateHandle
): ViewModel() {
    private val movieId = savedStateHandle.get<Int>("movieId")

    private val _movieDetailsState = MutableStateFlow(MovieDetailsState())
    val movieDetailsState = _movieDetailsState.asStateFlow()

    init {
        getMovie(movieId ?: -1)
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            _movieDetailsState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovie(id).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _movieDetailsState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _movieDetailsState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { movie ->
                            _movieDetailsState.update {
                                it.copy(
                                    movie = movie
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}