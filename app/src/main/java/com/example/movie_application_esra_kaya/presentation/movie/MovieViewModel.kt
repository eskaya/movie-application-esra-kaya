package com.example.movie_application_esra_kaya.presentation.movie

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
class MovieViewModel @Inject constructor(
    private val getPopularMovieListUseCase: GetMovieListUseCase
) : ViewModel() {


    private val _state = MutableLiveData<MovieListViewState>(MovieListViewState.Init)
    val getViewState: LiveData<MovieListViewState> get() = _state


    private fun setLoadingState(isLoading: Boolean) {
        _state.value = MovieListViewState.IsLoading(isLoading)
    }

     fun getPopularMovieList(type: String) {
        getPopularMovieListUseCase.invoke(type).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = MovieListViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = MovieListViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
}



