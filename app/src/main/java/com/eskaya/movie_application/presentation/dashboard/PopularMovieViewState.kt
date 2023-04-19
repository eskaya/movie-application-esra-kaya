package com.eskaya.movie_application.presentation.dashboard

import com.eskaya.movie_application.data.remote.models.response.MovieListDto

sealed class PopularMovieViewState {
    object Init : PopularMovieViewState()
    data class Success(val data: MovieListDto?) : PopularMovieViewState()
    data class IsLoading(val isLoading: Boolean) : PopularMovieViewState()
    data class Error(val error: Any) : PopularMovieViewState()
}