package com.eskaya.movie_application.presentation.movie_detail

import com.eskaya.movie_application.data.remote.models.response.ActorsDto

sealed class ActorsViewState {
    object Init : ActorsViewState()
    data class Success(val data: ActorsDto?) : ActorsViewState()
    data class IsLoading(val isLoading: Boolean) : ActorsViewState()
    data class Error(val error: Any) : ActorsViewState()
}