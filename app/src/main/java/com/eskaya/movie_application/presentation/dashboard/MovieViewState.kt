package com.eskaya.movie_application.presentation.dashboard

import com.eskaya.movie_application.data.remote.models.response.MovieListDto

sealed class MovieViewState {
    object Init : MovieViewState()
    data class Success(val data: MutableList<MovieListDto>) : MovieViewState()
    data class IsLoading(val isLoading: Boolean) : MovieViewState()
    data class Error(val error: Any) : MovieViewState()
}