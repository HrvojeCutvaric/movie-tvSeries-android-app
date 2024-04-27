package com.example.movieandroidapp.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandroidapp.domain.repositories.MovieListRepository
import com.example.movieandroidapp.domain.utils.Category
import com.example.movieandroidapp.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    val movieListRepository: MovieListRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow(MoviesState())
    var movieListState = _moviesState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MoviesUiEvent) {
        when (event) {
            is MoviesUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _moviesState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _moviesState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> {
                        _moviesState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularMovieList ->
                            _moviesState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList + popularMovieList,
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _moviesState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _moviesState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _moviesState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { upcomingMovieList ->
                            _moviesState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList + upcomingMovieList,
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}