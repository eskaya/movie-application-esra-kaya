package com.example.movie_application_esra_kaya.presentation.popular_movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_application_esra_kaya.domain.use_case.GetMovieDetailUseCase
import com.example.movie_application_esra_kaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    
    private val _state = MutableLiveData<MovieDetailViewState>(MovieDetailViewState.Init)
    val getViewState: LiveData<MovieDetailViewState> get() = _state

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = MovieDetailViewState.IsLoading(isLoading)
    }

    fun getMovieDetail(movie_id:Int) {
        movieDetailUseCase.invoke(movie_id).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = MovieDetailViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = MovieDetailViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }
}