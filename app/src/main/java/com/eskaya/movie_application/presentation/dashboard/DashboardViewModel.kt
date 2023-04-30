package com.eskaya.movie_application.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.domain.use_case.GetMovieListUseCase
import com.eskaya.movie_application.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPopularMovieListUseCase: GetMovieListUseCase,
    private val repository: MovieRepository
) : ViewModel() {


    private val _state = MutableLiveData<PopularMovieViewState>(PopularMovieViewState.Init)
    val getViewState: LiveData<PopularMovieViewState> get() = _state

    private val _topRatedState =
        MutableLiveData<TopRatedMovieViewState>(TopRatedMovieViewState.Init)
    val getTopRatedViewState: LiveData<TopRatedMovieViewState> get() = _topRatedState

    private val _upComingState =
        MutableLiveData<UpComingMovieViewState>(UpComingMovieViewState.Init)
    val getUpComingViewState: LiveData<UpComingMovieViewState> get() = _upComingState


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

    fun getUpComingMovieList(type: String) {
        getPopularMovieListUseCase.invoke(type).onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _upComingState.value = UpComingMovieViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _upComingState.value = UpComingMovieViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }

    // private val uiState = MutableLiveData<PopularMovieViewState>(PopularMovieViewState.Init)
    private val uiState = MutableLiveData<Resource<List<MovieListDto>>>()
     fun fetchUsers() {
        viewModelScope.launch {
            // uiState.postValue(Resource.Loading)
            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                    val usersFromApiDeferred = async { repository.getMovieList("popular") }
                    val moreUsersFromApiDeferred = async { repository.getMovieList("upcoming") }
                    val moreUsersFromApitest = async { repository.getMovieList("top_rated") }

                    val usersFromApi = usersFromApiDeferred.await()
                    val moreUsersFromApi = moreUsersFromApiDeferred.await()
                    val moremoreUsersFromApitest = moreUsersFromApitest.await()

                    val allUsersFromApi = mutableListOf<MovieListDto>()
                    allUsersFromApi.addAll(listOf(usersFromApi))
                    allUsersFromApi.addAll(listOf(moreUsersFromApi))
                    allUsersFromApi.addAll(listOf(moremoreUsersFromApitest))

                    uiState.postValue(Resource.Success(allUsersFromApi))
                }
            } catch (e: Exception) {
                uiState.postValue(Resource.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<Resource<List<MovieListDto>>> {
        return uiState
    }
}




