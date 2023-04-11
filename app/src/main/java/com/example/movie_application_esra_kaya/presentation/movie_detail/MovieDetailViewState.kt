package com.example.movie_application_esra_kaya.presentation.movie_detail

import com.example.movie_application_esra_kaya.data.remote.models.response.MovieDetailDto

sealed class MovieDetailViewState {
    object Init : MovieDetailViewState()
    data class Success(val data: MovieDetailDto?) : MovieDetailViewState()
    data class IsLoading(val isLoading: Boolean) : MovieDetailViewState()
    data class Error(val error: Any) : MovieDetailViewState()
}