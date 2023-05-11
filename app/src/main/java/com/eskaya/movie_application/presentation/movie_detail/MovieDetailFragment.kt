package com.eskaya.movie_application.presentation.movie_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.Cast
import com.eskaya.movie_application.data.remote.models.models.Genre
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.data.remote.models.response.MovieDetailDto
import com.eskaya.movie_application.databinding.FragmentPopularMovieDetailBinding
import com.eskaya.movie_application.presentation.adapter.ActorsAdapter
import com.eskaya.movie_application.presentation.adapter.GenresAdapter
import com.eskaya.movie_application.presentation.adapter.TrailerAdapterListener
import com.eskaya.movie_application.presentation.adapter.TrailersAdapter
import com.eskaya.movie_application.presentation.trailers_page.TrailerFragment
import com.eskaya.movie_application.utils.Constants
import com.eskaya.movie_application.utils.extensions.RecyclerViewItemDecorator
import com.eskaya.movie_application.utils.extensions.toFullImageLink
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieDetailBinding
    private var movieId: Int = 0
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var layoutManagerForActors: RecyclerView.LayoutManager? = null
    private var layoutManagerForTrailers: RecyclerView.LayoutManager? = null
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var actorsAdapter: ActorsAdapter
    private lateinit var trailersAdapter: TrailersAdapter
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listener()
        setUpObservers()
        setUpObserversForActors()
        setUpObserversTrailers()
        arguments?.let { movieId = it.getInt(Constants.MOVIE_ID) }
        movieId.let {
            viewModel.getMovieDetail(it)
            viewModel.getActors(it)
            viewModel.getTrailers(it)
        }
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        layoutManagerForActors = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        layoutManagerForTrailers = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerViewActors.layoutManager = layoutManagerForActors
        binding.recyclerViewTrailers.layoutManager = layoutManagerForTrailers
        val genresDecorator = RecyclerViewItemDecorator(
            spaceBetween = 24,
            spaceStart = 40
        )
        val actorsDecoration = RecyclerViewItemDecorator(
            spaceBetween = 24,
            spaceStart = 40,
        )
        val trailersDecoration = RecyclerViewItemDecorator(
            spaceBetween = 24,
            spaceStart = 40,
            spaceEnd = 40
        )
        binding.recyclerView.addItemDecoration(genresDecorator)
        binding.recyclerViewActors.addItemDecoration(actorsDecoration)
        binding.recyclerViewTrailers.addItemDecoration(trailersDecoration)
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

    private fun setUpObserversForActors() {
        viewModel.getActorsState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                ActorsViewState.Init -> Unit
                is ActorsViewState.Error -> handleErrorForActors(it.error)
                is ActorsViewState.IsLoading -> handleLoading(it.isLoading)
                is ActorsViewState.Success -> it.data?.let {
                    handleSuccessForActors(it.cast as ArrayList<Cast>)
                }
            }
        }
    }

    private fun setUpObserversTrailers() {
        viewModel.getTrailersState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                TrailersViewState.Init -> Unit
                is TrailersViewState.Error -> handleErrorForTrailers(it.error)
                is TrailersViewState.IsLoading -> handleLoading(it.isLoading)
                is TrailersViewState.Success -> it.data?.let {
                    handleSuccessForTrailers(it.results)
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
        binding.ratingBar.rating = (data.voteAverage / 2).toFloat()

        Glide.with(binding.root.context)
            .load(data.posterPath.toFullImageLink())
            .centerCrop()
            .placeholder(R.drawable.ic_cinema_placeholder)
            .into(binding.ivMoviePoster)

        setupRecyclerView(data.genres as ArrayList<Genre>)
    }

    private fun handleSuccessForActors(data: ArrayList<Cast>) {
        actorsAdapter = ActorsAdapter(data)
        binding.recyclerViewActors.adapter = actorsAdapter
    }

    private fun handleSuccessForTrailers(data: List<Trailer>) {
        val trailerList = arrayListOf<Trailer>()
        for (trailer in data) {
            if (trailer.site == "YouTube") {
                trailerList.add(trailer)
            }
        }
        trailersAdapter = TrailersAdapter(data,
            object : TrailerAdapterListener {
                override fun onClickedItem(position: Int) {
                    navigateTrailersPage(trailerList, position)
                }

            })
        binding.recyclerViewTrailers.adapter = trailersAdapter
    }

    private fun navigateTrailersPage(trailerList: ArrayList<Trailer>, position: Int) {
        val fragment = TrailerFragment.newInstance(trailerList, position)
        parentFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleError(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun handleErrorForActors(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun handleErrorForTrailers(error: Any) {
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