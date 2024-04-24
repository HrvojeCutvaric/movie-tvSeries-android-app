package com.example.movieandroidapp.domain.repositories

import com.example.movieandroidapp.domain.models.Movie
import com.example.movieandroidapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MovieListRepository {

    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int) : Flow<Resource<Movie>>

}