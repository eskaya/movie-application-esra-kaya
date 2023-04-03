package com.example.movie_application_esra_kaya.domain.repository

import com.example.movie_application_esra_kaya.data.remote.models.request.MovieListDto

interface MovieRepository {

    suspend fun getMovieList(): MovieListDto
}