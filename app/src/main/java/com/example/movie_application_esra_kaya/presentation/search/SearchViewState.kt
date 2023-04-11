package com.example.movie_application_esra_kaya.presentation.search

import com.example.movie_application_esra_kaya.data.remote.models.response.SearchDto

sealed class SearchViewState {
    object Init : SearchViewState()
    data class Success(val data: SearchDto?) : SearchViewState()
    data class IsLoading(val isLoading: Boolean) : SearchViewState()
    data class Error(val error: Any) : SearchViewState()
}