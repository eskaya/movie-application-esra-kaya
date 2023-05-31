package com.eskaya.movie_application.presentation.movie_detail

import com.eskaya.movie_application.data.remote.models.response.TrailersDto

sealed class TrailersViewState {
    object Init : TrailersViewState()
    data class Success(val data: TrailersDto?) : TrailersViewState()
    data class IsLoading(val isLoading: Boolean) : TrailersViewState()
    data class Error(val error: Any) : TrailersViewState()
}