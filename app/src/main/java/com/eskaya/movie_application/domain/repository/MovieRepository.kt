package com.eskaya.movie_application.domain.repository

import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.data.remote.models.response.SearchDto

interface MovieRepository {

    suspend fun getMovieList(type: String): MovieListDto
    suspend fun getMovieDetail(movieId: Int): MovieDetailDto
    suspend fun getSearchResult(query: String): SearchDto
}