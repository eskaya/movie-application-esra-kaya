package com.example.movie_application_esra_kaya.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_application_esra_kaya.domain.use_case.GetMovieListUseCase
import com.example.movie_application_esra_kaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovieListUseCase: GetMovieListUseCase
) : ViewModel() {


    private val _state = MutableLiveData<HomeViewState>(HomeViewState.Init)
    val getViewState: LiveData<HomeViewState> get() = _state

    init {
        getPopularMovieList("popular")
    }

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = HomeViewState.IsLoading(isLoading)
    }

    private fun getPopularMovieList(type: String) {
        getPopularMovieListUseCase.invoke(type).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = HomeViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = HomeViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
}



