package com.example.movie_application_esra_kaya.presentation.home

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieListDto

sealed class HomeViewState {
    object Init : HomeViewState()
    data class Success(val data: MovieListDto?) : HomeViewState()
    data class IsLoading(val isLoading: Boolean) : HomeViewState()
    data class Error(val error: Any) : HomeViewState()
}