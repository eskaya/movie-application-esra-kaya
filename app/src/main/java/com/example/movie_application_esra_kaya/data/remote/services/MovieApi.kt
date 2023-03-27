package com.example.movie_application_esra_kaya.data.remote.services

import com.example.movie_application_esra_kaya.data.remote.dto.MovieListDto
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/popular ")
    suspend fun getMovieList(): List<MovieListDto>

}