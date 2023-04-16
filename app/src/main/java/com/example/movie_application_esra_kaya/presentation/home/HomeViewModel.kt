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


    private val _state = MutableLiveData<PopularMovieViewState>(PopularMovieViewState.Init)
    val getViewState: LiveData<PopularMovieViewState> get() = _state

    private val _topRatedState = MutableLiveData<TopRatedMovieViewState>(TopRatedMovieViewState.Init)
    val getTopRatedViewState: LiveData<TopRatedMovieViewState> get() = _topRatedState


    private fun setLoadingState(isLoading: Boolean) {
        _state.value = PopularMovieViewState.IsLoading(isLoading)
    }

     fun getPopularMovieList(type: String) {
        getPopularMovieListUseCase.invoke(type).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = PopularMovieViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = PopularMovieViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
    fun getTopRatedMovieList(type: String) {
        getPopularMovieListUseCase.invoke(type).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _topRatedState.value = TopRatedMovieViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _topRatedState.value = TopRatedMovieViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
}



