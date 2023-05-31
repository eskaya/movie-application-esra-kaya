package com.eskaya.movie_application.presentation.search

import com.eskaya.movie_application.data.remote.models.response.SearchDto

sealed class SearchViewState {
    object Init : SearchViewState()
    data class Success(val data: SearchDto?) : SearchViewState()
    data class IsLoading(val isLoading: Boolean) : SearchViewState()
    data class Error(val error: Any) : SearchViewState()
}