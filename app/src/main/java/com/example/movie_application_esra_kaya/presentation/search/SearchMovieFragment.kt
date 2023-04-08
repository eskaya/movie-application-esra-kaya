package com.example.movie_application_esra_kaya.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.databinding.FragmentSearchMovieBinding
import com.example.movie_application_esra_kaya.presentation.movie.PopularMovieListAdapter

class SearchMovieFragment : Fragment() {
    private lateinit var binding:FragmentSearchMovieBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var movieListAdapter: PopularMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init(){
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }
}