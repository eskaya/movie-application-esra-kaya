package com.eskaya.movie_application.presentation.movie_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.Genre
import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.databinding.FragmentPopularMovieDetailBinding
import com.eskaya.movie_application.presentation.adapter.GenresAdapter
import com.eskaya.movie_application.utils.Constants
import com.eskaya.movie_application.utils.extensions.toFullImageLink
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieDetailBinding
    private var movieId by Delegates.notNull<Int>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var genresAdapter: GenresAdapter
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMovieDetailBinding.inflate(layoutInflater)
        arguments?.let {
            movieId = it.getInt(Constants.MOVIE_ID)
        }
        movieId.let {
            viewModel.getMovieDetail(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listener()
        setUpObservers()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun setUpObservers() {
        viewModel.getViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                MovieDetailViewState.Init -> Unit
                is MovieDetailViewState.Error -> handleError(it.error)
                is MovieDetailViewState.IsLoading -> handleLoading(it.isLoading)
                is MovieDetailViewState.Success -> it.data?.let {
                    handleSuccess(
                        it
                    )
                }
            }
        }
    }

    private fun listener() {
        binding.ivClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun handleSuccess(data: MovieDetailDto) {
        binding.tvTitle.text = data.title
        binding.tvDate.text = data.releaseDate
        binding.tvOverview.text = data.overview
        binding.tvImdb.text = data.voteCount.toString()
        binding.ratingBar.rating = (data.voteAverage/2).toFloat()

        Glide.with(binding.root.context)
            .load( data.posterPath.toFullImageLink())
            .centerCrop()
            .placeholder(R.drawable.ic_cinema_placeholder)
            .into(binding.ivMoviePoster)

        setupRecyclerView(data.genres as ArrayList<Genre>)
    }


    private fun handleLoading(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleError(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }


    private fun setupRecyclerView(data: ArrayList<Genre>) {
        genresAdapter = GenresAdapter(data)
        binding.recyclerView.adapter = genresAdapter
    }

    companion object {
        fun newInstance(movieId: Int) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constants.MOVIE_ID, movieId)
                }
            }
    }
}