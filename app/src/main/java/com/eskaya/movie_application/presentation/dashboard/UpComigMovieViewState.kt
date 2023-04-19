package com.eskaya.movie_application.presentation.dashboard

import com.eskaya.movie_application.data.remote.models.response.MovieListDto

sealed class UpComingMovieViewState {
    object Init : UpComingMovieViewState()
    data class Success(val data: MovieListDto?) : UpComingMovieViewState()
    data class IsLoading(val isLoading: Boolean) : UpComingMovieViewState()
    data class Error(val error: Any) : UpComingMovieViewState()
}