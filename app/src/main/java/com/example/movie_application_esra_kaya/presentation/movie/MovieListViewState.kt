package com.example.movie_application_esra_kaya.presentation.movie

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto

sealed class MovieListViewState {
    object Init : MovieListViewState()
    data class Success(val data: MovieListDto?) : MovieListViewState()
    data class IsLoading(val isLoading: Boolean) : MovieListViewState()
    data class Error(val error: Any) : MovieListViewState()
}