package com.example.movie_application_esra_kaya.presentation.home

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto

sealed class UpComingMovieViewState {
    object Init : UpComingMovieViewState()
    data class Success(val data: MovieListDto?) : UpComingMovieViewState()
    data class IsLoading(val isLoading: Boolean) : UpComingMovieViewState()
    data class Error(val error: Any) : UpComingMovieViewState()
}