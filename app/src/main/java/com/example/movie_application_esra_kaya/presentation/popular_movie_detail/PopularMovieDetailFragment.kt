package com.example.movie_application_esra_kaya.presentation.popular_movie_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_application_esra_kaya.databinding.FragmentPopularMovieDetailBinding
import com.example.movie_application_esra_kaya.utils.Constants


class PopularMovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieDetailBinding
    private var movieId: Int? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var genresAdapter: GenresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMovieDetailBinding.inflate(layoutInflater)
        arguments?.let {
            movieId = it.getInt(Constants.MOVIE_ID)
        }
        init()
        listener()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        if (movieId != null) {
            Toast.makeText(context, "movieId: ${movieId.toString()}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listener() {
        binding.ivClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView(){
       // genresAdapter = GenresAdapter()
        binding.recyclerView.adapter = genresAdapter
    }

    companion object {
        fun newInstance(movieId: Int) =
            PopularMovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.MOVIE_ID, movieId)
                }
            }

    }
}