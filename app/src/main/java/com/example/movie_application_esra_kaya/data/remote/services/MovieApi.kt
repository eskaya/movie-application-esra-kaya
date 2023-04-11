package com.example.movie_application_esra_kaya.data.remote.services

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieDetailDto
import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto
import com.example.movie_application_esra_kaya.data.remote.models.response.SearchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getMovieList(): MovieListDto

    @GET("movie/{movie_id}?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailDto

    @GET("search/movie?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getSearchResult(@Query("query") query: String): SearchDto
}