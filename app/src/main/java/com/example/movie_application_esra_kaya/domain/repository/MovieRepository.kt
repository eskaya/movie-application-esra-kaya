package com.example.movie_application_esra_kaya.domain.repository

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieDetailDto
import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto
import com.example.movie_application_esra_kaya.data.remote.models.response.SearchDto

interface MovieRepository {

    suspend fun getMovieList(type: String): MovieListDto
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
    suspend fun getSearchResult(query: String): SearchDto
}