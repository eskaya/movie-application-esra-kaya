package com.example.movie_application_esra_kaya.domain.repository

import com.example.movie_application_esra_kaya.data.remote.models.request.MovieDetailDto
import com.example.movie_application_esra_kaya.data.remote.models.request.MovieListDto
import com.example.movie_application_esra_kaya.data.remote.models.request.SearchDto
import retrofit2.http.Query

interface MovieRepository {

    suspend fun getMovieList(): MovieListDto

    suspend fun getMovieDetail(movie_id: Int): MovieDetailDto
    suspend fun getSearchResult(query: String): SearchDto
}