package com.eskaya.movie_application.presentation.movie_list

import com.eskaya.movie_application.data.remote.models.response.MovieListDto

sealed class MovieListViewState {
    object Init : MovieListViewState()
    data class Success(val data: MovieListDto?) : MovieListViewState()
    data class IsLoading(val isLoading: Boolean) : MovieListViewState()
    data class Error(val error: Any) : MovieListViewState()
}