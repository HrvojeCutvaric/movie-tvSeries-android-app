package com.example.movieandroidapp.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandroidapp.domain.repositories.MovieListRepository
import com.example.movieandroidapp.domain.utils.Category
import com.example.movieandroidapp.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow(MoviesState())
    var moviesState = _moviesState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
        getNowPlayingMovieList(false)
    }

    fun onEvent(event: MoviesUiEvent) {
        when (event) {
            is MoviesUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                } else if (event.category == Category.PLAYING) {
                    getNowPlayingMovieList(true)
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
                moviesState.value.popularMovieListPage
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
                                    popularMovieList = moviesState.value.popularMovieList + popularMovieList.shuffled(),
                                    popularMovieListPage = moviesState.value.popularMovieListPage + 1
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
                moviesState.value.popularMovieListPage
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
                                    upcomingMovieList = moviesState.value.upcomingMovieList + upcomingMovieList.shuffled(),
                                    upcomingMovieListPage = moviesState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getNowPlayingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _moviesState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.PLAYING,
                moviesState.value.nowPlayingMovieListPage
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
                        result.data?.let { nowPlayingMovieList ->
                            _moviesState.update {
                                it.copy(
                                    nowPlayingMovieList = moviesState.value.nowPlayingMovieList + nowPlayingMovieList.shuffled(),
                                    nowPlayingMovieListPage = moviesState.value.nowPlayingMovieListPage + 1
                                )
                            }

                        }
                    }
                }
            }
        }
    }

}