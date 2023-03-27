package com.example.movie_application_esra_kaya.presentation.home

import com.example.movie_application_esra_kaya.data.remote.dto.MovieListDto


data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<MovieListDto> = emptyList(),
    val error: String = ""
)