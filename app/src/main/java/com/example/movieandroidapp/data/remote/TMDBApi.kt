package com.example.movieandroidapp.data.remote

import com.example.movieandroidapp.data.remote.respond.movie.GenreListDto
import com.example.movieandroidapp.data.remote.respond.movie.MovieListDto
import com.example.movieandroidapp.data.remote.respond.movie.details.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieListDto

    @GET("genre/movie/list")
    suspend fun getGenreListForMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): GenreListDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsDto


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "<place api key>"
    }

}