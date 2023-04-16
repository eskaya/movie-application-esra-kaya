package com.example.movie_application_esra_kaya.presentation.home

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto

sealed class TopRatedMovieViewState {
    object Init : TopRatedMovieViewState()
    data class Success(val data: MovieListDto?) : TopRatedMovieViewState()
    data class IsLoading(val isLoading: Boolean) : TopRatedMovieViewState()
    data class Error(val error: Any) : TopRatedMovieViewState()
}