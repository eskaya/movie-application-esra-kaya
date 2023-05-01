package com.eskaya.movie_application.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.domain.use_case.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPopularMovieListUseCase: GetMovieListUseCase,
    private val repository: MovieRepository
) : ViewModel() {


     private val uiState = MutableLiveData<MovieViewState>(MovieViewState.Init)

     fun fetchUsers() {
        viewModelScope.launch {
             //uiState.postValue(PopularMovieViewState.IsLoading)
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

                    uiState.postValue(MovieViewState.Success(allUsersFromApi))
                }
            } catch (e: Exception) {
                uiState.postValue(MovieViewState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<MovieViewState> {
        return uiState
    }
}




