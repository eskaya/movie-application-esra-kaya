package com.example.movie_application_esra_kaya.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_application_esra_kaya.data.remote.dto.MovieListDto
import com.example.movie_application_esra_kaya.domain.use_case.GetPopularMovieListUseCase
import com.example.movie_application_esra_kaya.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBankAccountsUseCase: GetPopularMovieListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<BankAccountsViewState>(BankAccountsViewState.Init)
    fun getViewState(): StateFlow<BankAccountsViewState> = _state.asStateFlow()

    init {
        getBankAccounts()
    }

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = BankAccountsViewState.IsLoading(isLoading)
    }

    private fun getBankAccounts() {
        getBankAccountsUseCase.invoke().onEach {
            when (it) {
                is Resource.Error -> {
                    setLoadingState(false)
                    _state.value = BankAccountsViewState.Error(it.message as Any)
                }
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    _state.value = BankAccountsViewState.Success(it.data)
                }
            }

        }.launchIn(viewModelScope)
    }

    sealed class BankAccountsViewState {
        object Init : BankAccountsViewState()
        data class Success(val data: MovieListDto?) : BankAccountsViewState()
        data class IsLoading(val isLoading: Boolean) : BankAccountsViewState()
        data class Error(val error: Any) : BankAccountsViewState()
    }

}


