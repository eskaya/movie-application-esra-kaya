package com.eskaya.movie_application.presentation.home

import com.eskaya.movie_application.data.remote.models.response.MovieListDto

sealed class TopRatedMovieViewState {
    object Init : TopRatedMovieViewState()
    data class Success(val data: MovieListDto?) : TopRatedMovieViewState()
    data class IsLoading(val isLoading: Boolean) : TopRatedMovieViewState()
    data class Error(val error: Any) : TopRatedMovieViewState()
}