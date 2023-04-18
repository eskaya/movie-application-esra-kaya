package com.eskaya.movie_application.presentation.movie_detail

import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto

sealed class MovieDetailViewState {
    object Init : MovieDetailViewState()
    data class Success(val data: MovieDetailDto?) : MovieDetailViewState()
    data class IsLoading(val isLoading: Boolean) : MovieDetailViewState()
    data class Error(val error: Any) : MovieDetailViewState()
}