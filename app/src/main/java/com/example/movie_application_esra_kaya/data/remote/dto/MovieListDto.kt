package com.example.movie_application_esra_kaya.data.remote.dto

data class MovieListDto(
    val page: Int,
    val results: List<MovieItem>,
    val total_pages: Int,
    val total_results: Int
)