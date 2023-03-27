package com.example.movie_application_esra_kaya.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.movie_application_esra_kaya.data.remote.dto.MovieItem
import com.example.movie_application_esra_kaya.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: HomeViewModel by viewModels()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var bankAccountsAdapter: PopularMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        /*
        val state = viewModel.state.value

        if(state.movies !=null){
            println(state)
        }
        if(state.error.isNotBlank()){
        }
        if(state.isLoading){
            println("loading")
        }

         */
        init()
        setUpObservers()
        return binding.root
    }

    private fun init(){
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.recyclerView.layoutManager = layoutManager
    }
    private fun setUpObservers() {
        viewModel.getViewState()
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: HomeViewModel.BankAccountsViewState) {
        when (state) {
            HomeViewModel.BankAccountsViewState.Init -> Unit
            is HomeViewModel.BankAccountsViewState.Error -> handleError(state.error)
            is HomeViewModel.BankAccountsViewState.IsLoading -> handleLoading(state.isLoading)
            is HomeViewModel.BankAccountsViewState.Success -> state.data?.let {
                handleSuccess(
                    it.results as List<MovieItem>
                )
            }
        }
    }

    private fun handleSuccessWithEmptyList() {

    }

    private fun handleSuccess(data: List<MovieItem>) {
        println(data)
        bankAccountsAdapter = PopularMovieListAdapter(data)
        binding.recyclerView.adapter = bankAccountsAdapter
    }

    private fun handleLoading(loading: Boolean) {

    }

    private fun handleError(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }


}