package com.eskaya.movie_application.presentation.movie

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
import com.eskaya.movie_application.R

import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.databinding.FragmentMovieListBinding
import com.eskaya.movie_application.presentation.adapter.MovieListAdapter
import com.eskaya.movie_application.presentation.adapter.PopularMovieAdapterListener
import com.eskaya.movie_application.presentation.movie_detail.MovieDetailFragment
import com.eskaya.movie_application.presentation.search.SearchMovieFragment
import com.eskaya.movie_application.utils.Constants
import com.eskaya.movie_application.utils.MovieTypes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieViewModel by viewModels()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var popularMovieListAdapter: MovieListAdapter
    private var type: String = MovieTypes.POPULAR

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        arguments?.let {
            type = it.getString(Constants.MOVIE_TYPE).toString()
        }
        type.let {
            viewModel.getPopularMovieList(type)
        }
        init()
        listener()
        setUpObservers()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun listener() {
        binding.cvSearch.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.frameLayout, SearchMovieFragment())
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setUpObservers() {
        viewModel.getViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                MovieListViewState.Init -> Unit
                is MovieListViewState.Error -> handleError(it.error)
                is MovieListViewState.IsLoading -> handleLoading(it.isLoading)
                is MovieListViewState.Success -> it.data?.let {
                    handleSuccess(
                        it.results
                    )
                }
            }
        }
    }

    private fun handleSuccess(data: List<MovieItem>) {
        popularMovieListAdapter = MovieListAdapter(data,
            object : PopularMovieAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }

            })
        binding.recyclerView.adapter = popularMovieListAdapter
    }


    private fun handleLoading(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleError(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun navigationMovieDetailPage(movieId: Int) {
        val fragment = MovieDetailFragment.newInstance(movieId)

        parentFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    companion object {
        fun newInstance(type: String) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.MOVIE_TYPE, type)
                }
            }
    }

}