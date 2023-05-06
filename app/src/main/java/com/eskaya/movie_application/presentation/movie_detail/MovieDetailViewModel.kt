package com.eskaya.movie_application.presentation.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eskaya.movie_application.domain.use_case.GetActorsUseCase
import com.eskaya.movie_application.domain.use_case.GetMovieDetailUseCase
import com.eskaya.movie_application.domain.use_case.GetMovieTrailersUseCase
import com.eskaya.movie_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val actorsUseCase: GetActorsUseCase,
    private val trailersUseCase: GetMovieTrailersUseCase
) : ViewModel() {

    private val _state = MutableLiveData<MovieDetailViewState>(MovieDetailViewState.Init)
    val getViewState: LiveData<MovieDetailViewState> get() = _state

    private val _actorsState = MutableLiveData<ActorsViewState>(ActorsViewState.Init)
    val getActorsState: LiveData<ActorsViewState> get() = _actorsState

    private val _trailersState = MutableLiveData<TrailersViewState>(TrailersViewState.Init)
    val getTrailersState: LiveData<TrailersViewState> get() = _trailersState

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = MovieDetailViewState.IsLoading(isLoading)
    }

    fun getMovieDetail(movieId: Int) {
        movieDetailUseCase.invoke(movieId).onEach {
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

    fun getActors(movieId: Int) {
        actorsUseCase.invoke(movieId).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _actorsState.value = ActorsViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _actorsState.value = ActorsViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getTrailers(movieId: Int) {
        trailersUseCase.invoke(movieId).onEach {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Error -> {
                    setLoadingState(false)
                    _trailersState.value = it.message?.let { it1 -> TrailersViewState.Error(it1) }
                }
                is Resource.Success -> {
                    _trailersState.value = TrailersViewState.Success(it.data)
                }
            }
        }
    }
}