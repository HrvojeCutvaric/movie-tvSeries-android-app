package com.example.movieandroidapp.data.mappers

import com.example.movieandroidapp.data.local.movie.MovieEntity
import com.example.movieandroidapp.data.remote.respond.movie.GenreDto
import com.example.movieandroidapp.data.remote.respond.movie.MovieDto
import com.example.movieandroidapp.data.remote.respond.movie.details.BelongsToCollectionDto
import com.example.movieandroidapp.data.remote.respond.movie.details.MovieDetailsDto
import com.example.movieandroidapp.data.remote.respond.movie.details.ProductionCompanyDto
import com.example.movieandroidapp.data.remote.respond.movie.details.ProductionCountryDto
import com.example.movieandroidapp.data.remote.respond.movie.details.SpokenLanguageDto
import com.example.movieandroidapp.domain.models.movie.Genre
import com.example.movieandroidapp.domain.models.movie.Movie
import com.example.movieandroidapp.domain.models.movie.details.BelongsToCollection
import com.example.movieandroidapp.domain.models.movie.details.MovieDetails
import com.example.movieandroidapp.domain.models.movie.details.ProductionCompany
import com.example.movieandroidapp.domain.models.movie.details.ProductionCountry
import com.example.movieandroidapp.domain.models.movie.details.SpokenLanguage

fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,

        category = category,

        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        id = id,
        original_title = original_title,
        video = video,

        category = category,

        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        }
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        belongs_to_collection = belongs_to_collection?.toBelongsToCollection()
            ?: BelongsToCollection(
                "",
                -1,
                "",
                ""
            ),
        budget = budget ?: 0,
        genres = convertToListGenres(genres),
        homepage = homepage ?: "",
        id = id ?: -1,
        imdb_id = imdb_id ?: "",
        origin_country = origin_country ?: listOf(),
        original_language = original_language ?: "",
        original_title = original_title ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        poster_path = poster_path ?: "",
        production_companies = covertToListProductionCompanies(production_companies),
        production_countries = covertToListProductionCountries(production_countries),
        release_date = release_date ?: "",
        revenue = revenue ?: 0,
        runtime = runtime ?: 0,
        spoken_languages = convertToListSpokenLanguages(spoken_languages),
        status = status ?: "",
        tagline = tagline ?: "",
        title = title ?: "",
        video = video ?: false,
        vote_average = vote_average ?: 0.0,
        vote_count = vote_count ?: 0
    )
}

private fun convertToListSpokenLanguages(spokenLanguagesDtoList: List<SpokenLanguageDto>?): List<SpokenLanguage> {
    val convertedList = mutableListOf<SpokenLanguage>()

    spokenLanguagesDtoList?.forEach { spokenLanguageDto ->
        convertedList.add(spokenLanguageDto.toSpokenLanguage())
    }

    return convertedList
}

private fun covertToListProductionCountries(productionCountryDtoList: List<ProductionCountryDto>?): List<ProductionCountry> {
    val convertedList = mutableListOf<ProductionCountry>()

    productionCountryDtoList?.forEach { productionCountryDto ->
        convertedList.add(productionCountryDto.toProductionCountry())
    }

    return convertedList
}

private fun convertToListGenres(genreDtoList: List<GenreDto>?): List<Genre> {
    val convertedList = mutableListOf<Genre>()

    genreDtoList?.forEach { genreDto ->
        convertedList.add(genreDto.toGenre())
    }

    return convertedList
}

private fun covertToListProductionCompanies(productionCompanyDtoList: List<ProductionCompanyDto>?): List<ProductionCompany> {
    val convertedList = mutableListOf<ProductionCompany>()

    productionCompanyDtoList?.forEach { productionCompanyDto ->
        convertedList.add(productionCompanyDto.toProductionCompany())
    }

    return convertedList
}

fun BelongsToCollectionDto.toBelongsToCollection(): BelongsToCollection {
    return BelongsToCollection(
        backdrop_path = backdrop_path ?: "",
        id = id ?: -1,
        name = name ?: "",
        poster_path = poster_path ?: ""
    )
}

fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id ?: -1,
        name = name ?: ""
    )
}

fun ProductionCompanyDto.toProductionCompany(): ProductionCompany {
    return ProductionCompany(
        id = id ?: -1,
        logo_path = logo_path ?: "",
        name = name ?: "",
        origin_country = origin_country ?: ""
    )
}

fun ProductionCountryDto.toProductionCountry(): ProductionCountry {
    return ProductionCountry(
        iso_3166_1 = iso_3166_1 ?: "",
        name = name ?: ""
    )
}

fun SpokenLanguageDto.toSpokenLanguage(): SpokenLanguage {
    return SpokenLanguage(
        english_name = english_name ?: "",
        iso_639_1 = iso_639_1 ?: "",
        name = name ?: ""
    )
}