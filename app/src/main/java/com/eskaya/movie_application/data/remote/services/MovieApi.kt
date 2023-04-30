package com.eskaya.movie_application.data.remote.services

import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.data.remote.models.response.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{type}?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getMovieList(@Path("type") type: String): MovieListDto

    @GET("movie/{movieId}?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDto

    @GET("search/movie?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getSearchResult(@Query("query") query: String): SearchDto

    @GET("movie/{movieId}/credits?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getMovieActors(@Path("movieId") movieId: Int): MovieDetailDto
}