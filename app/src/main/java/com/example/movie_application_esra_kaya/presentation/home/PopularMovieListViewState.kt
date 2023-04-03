package com.example.movie_application_esra_kaya.presentation.home

import com.example.movie_application_esra_kaya.data.remote.models.request.MovieListDto

sealed class PopularMovieListViewState {
    object Init : PopularMovieListViewState()
    data class Success(val data: MovieListDto?) : PopularMovieListViewState()
    data class IsLoading(val isLoading: Boolean) : PopularMovieListViewState()
    data class Error(val error: Any) : PopularMovieListViewState()
}