package com.eskaya.movie_application.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eskaya.movie_application.domain.use_case.GetSearchUseCase
import com.eskaya.movie_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase
) : ViewModel() {


    private val _state = MutableLiveData<SearchViewState>(SearchViewState.Init)
    val getViewState: LiveData<SearchViewState> get() = _state

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = SearchViewState.IsLoading(isLoading)
    }

    fun getSearchList(query: String) {
        getSearchUseCase.invoke(query).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = SearchViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = SearchViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
}



