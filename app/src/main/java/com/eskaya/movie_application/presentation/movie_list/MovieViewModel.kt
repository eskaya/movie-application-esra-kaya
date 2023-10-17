package com.eskaya.movie_application.presentation.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eskaya.movie_application.domain.use_case.GetMovieListUseCase
import com.eskaya.movie_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {


    private val _state = MutableLiveData<MovieListViewState>(MovieListViewState.Init)
    val getViewState: LiveData<MovieListViewState> get() = _state


    private fun setLoadingState(isLoading: Boolean) {
        _state.value = MovieListViewState.IsLoading(isLoading)
    }

     fun getMovieList(type: String) {
        getMovieListUseCase.invoke(type).onEach {
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



