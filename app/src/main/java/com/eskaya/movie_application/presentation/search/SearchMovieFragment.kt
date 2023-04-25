package com.eskaya.movie_application.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.databinding.FragmentSearchMovieBinding
import com.eskaya.movie_application.presentation.adapter.MovieAdapterListener
import com.eskaya.movie_application.presentation.adapter.MovieListAdapter
import com.eskaya.movie_application.presentation.movie_detail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SearchMovieFragment : Fragment() {
    private lateinit var binding: FragmentSearchMovieBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var movieListAdapter: MovieListAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(layoutInflater)
        init()
        listener()
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO --> keyboard not open
        binding.etSearch.requestFocus()
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun listener() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length >= 2) {
                    timer = Timer()
                    timer?.schedule(
                        object : TimerTask() {
                            override fun run() {
                                viewModel.getSearchList(s.toString())
                            }

                        }, 1000
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.tvSearchResult.text = s
                if (timer != null) {
                    timer?.cancel()
                }
            }
        })

        binding.cvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setUpObservers() {
        viewModel.getViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                SearchViewState.Init -> Unit
                is SearchViewState.Error -> handleError(it.error)
                is SearchViewState.IsLoading -> handleLoading(it.isLoading)
                is SearchViewState.Success -> it.data?.let {
                    handleSuccess(
                        it.results
                    )
                }
            }
        }
    }

    private fun handleSuccess(data: List<MovieItem>) {
        if (data.isEmpty()) {
            binding.tvNotFoundMovie.visibility = View.VISIBLE
        }
        binding.tvNotFoundMovie.visibility = View.GONE
        movieListAdapter = MovieListAdapter(data,
            object : MovieAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }

            })
        binding.recyclerView.adapter = movieListAdapter
    }


    private fun handleLoading(loading: Boolean) {
        // binding.containerProgress.isVisible = loading
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
}