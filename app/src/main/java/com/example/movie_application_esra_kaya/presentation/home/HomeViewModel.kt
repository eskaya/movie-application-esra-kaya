package com.example.movie_application_esra_kaya.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_application_esra_kaya.domain.use_case.GetPopularMovieListUseCase
import com.example.movie_application_esra_kaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieListUseCase: GetPopularMovieListUseCase
) : ViewModel() {
     val _state = MutableStateFlow<PopularMovieListViewState>(PopularMovieListViewState.Init)
    fun getViewState(): StateFlow<PopularMovieListViewState> = _state.asStateFlow()

    init {
        getPopularMovieList()
    }

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = PopularMovieListViewState.IsLoading(isLoading)
    }

    private fun getPopularMovieList() {
        getPopularMovieListUseCase.invoke().onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = PopularMovieListViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = PopularMovieListViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
}


