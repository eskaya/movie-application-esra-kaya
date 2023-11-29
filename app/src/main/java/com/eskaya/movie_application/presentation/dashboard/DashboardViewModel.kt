package com.eskaya.movie_application.presentation.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.eskaya.movie_application.common.base.BaseViewModel
import com.eskaya.movie_application.data.db.AppDatabase
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.data.remote.models.response.MovieListDto
import com.eskaya.movie_application.domain.repository.MovieRepository
import com.eskaya.movie_application.domain.use_case.GetMovieListUseCase
import com.eskaya.movie_application.utils.MovieTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val application: Application,
    private val getMovieListUseCase: GetMovieListUseCase,
    private val repository: MovieRepository
) : BaseViewModel(application) {


    private val uiState = MutableLiveData<MovieViewState>(MovieViewState.Init)

    fun getMovies() {
        viewModelScope.launch {
            //uiState.postValue(PopularMovieViewState.IsLoading)
            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                    val popularMovieDeferred = async { repository.getMovieList(MovieTypes.POPULAR) }
                    val upcomingMovieDeferred =
                        async { repository.getMovieList(MovieTypes.UPCOMING) }
                    val topRatedMovieDeferred =
                        async { repository.getMovieList(MovieTypes.TOP_RATED) }

                    val popularMovieFromApi = popularMovieDeferred.await()
                    val upcomingMovieFromApi = upcomingMovieDeferred.await()
                    val topRatedMovieFromApi = topRatedMovieDeferred.await()

                    val allMovieFromApi = mutableListOf<MovieListDto>()
                    allMovieFromApi.addAll(listOf(popularMovieFromApi))
                    allMovieFromApi.addAll(listOf(upcomingMovieFromApi))
                    allMovieFromApi.addAll(listOf(topRatedMovieFromApi))

                    uiState.postValue(MovieViewState.Success(allMovieFromApi))
                }
            } catch (e: Exception) {
                uiState.postValue(MovieViewState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<MovieViewState> {
        return uiState
    }

     fun saveMovie(movieItem: MovieItem){
        launch {
            val dao = AppDatabase(application).movieDao()
            dao.insert(movieItem)
        }
    }
}




