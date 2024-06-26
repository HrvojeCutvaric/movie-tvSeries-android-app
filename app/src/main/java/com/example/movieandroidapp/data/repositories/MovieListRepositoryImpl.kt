package com.example.movieandroidapp.data.repositories

import com.example.movieandroidapp.data.local.movie.MovieDatabase
import com.example.movieandroidapp.data.mappers.toMovie
import com.example.movieandroidapp.data.mappers.toMovieDetails
import com.example.movieandroidapp.data.mappers.toMovieEntity
import com.example.movieandroidapp.data.remote.TMDBApi
import com.example.movieandroidapp.domain.models.movie.Movie
import com.example.movieandroidapp.domain.models.movie.details.MovieDetails
import com.example.movieandroidapp.domain.repositories.MovieListRepository
import com.example.movieandroidapp.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val tmdbApi: TMDBApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                tmdbApi.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(movieEntities.map { movieEntity -> movieEntity.toMovie(category) }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if(movieEntity != null){
                emit(Resource.Success(movieEntity.toMovie(movieEntity.category)))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(message = "Error no such movie"))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> {
        return flow {
            emit(Resource.Loading(true))

            val movieDetailsFromApi = try {
                tmdbApi.getMovieDetails(id)
            }catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            emit(Resource.Success(movieDetailsFromApi.toMovieDetails()))
            emit(Resource.Loading(false))
        }
    }


}