package com.eskaya.movie_application.data.remote.services

import com.eskaya.movie_application.data.remote.models.response.ActorsDto
import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.data.remote.models.response.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{type}")
    suspend fun getMovieList(@Path("type") type: String): MovieListDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDto

    @GET("search/movie")
    suspend fun getSearchResult(@Query("query") query: String): SearchDto

    @GET("movie/{movieId}/credits")
    suspend fun getMovieActors(@Path("movieId") movieId: Int): ActorsDto
}