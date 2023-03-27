package com.example.movie_application_esra_kaya.data.remote.services

import com.example.movie_application_esra_kaya.data.remote.dto.MovieListDto
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/popular?api_key=46d0b7df20b6dca0b123566e3d926b4b")
    suspend fun getMovieList(): MovieListDto

}