package com.example.movie_application_esra_kaya.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movie_application_esra_kaya.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val state = viewModel.state.value

        if(state.movies !=null){
            println(state)
        }
        if(state.error.isNotBlank()){
        }
        if(state.isLoading){
            println("loading")
        }
        return binding.root
    }

}