package com.example.movie_application_esra_kaya.presentation.popular_movie_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movie_application_esra_kaya.databinding.FragmentPopularMovieDetailBinding


class PopularMovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieDetailBinding
    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMovieDetailBinding.inflate(layoutInflater)
        arguments?.let {
            movieId = it.getInt(movieId.toString())
        }
        init()
        return binding.root
    }

    private fun init() {
        if (movieId != null) {
            Toast.makeText(context, "movieId: $movieId", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance(movieId: Int) =
            PopularMovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(movieId.toString(), null)
                }
            }
    }
}