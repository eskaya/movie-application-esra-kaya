package com.eskaya.movie_application.domain.repository

import com.eskaya.movie_application.data.remote.models.response.*

interface MovieRepository {

    suspend fun getMovieList(type: String): MovieListDto
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
    suspend fun getSearchResult(query: String): SearchDto
    suspend fun getMovieActors(movieId: Int): ActorsDto
    suspend fun getMovieTrailers(movieId: Int): TrailersDto
}