package com.example.movie_application_esra_kaya.presentation.home

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto

sealed class PopularMovieViewState {
    object Init : PopularMovieViewState()
    data class Success(val data: MovieListDto?) : PopularMovieViewState()
    data class IsLoading(val isLoading: Boolean) : PopularMovieViewState()
    data class Error(val error: Any) : PopularMovieViewState()
}