package com.eskaya.movie_application.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.presentation.adapter.MovieAdapterListener
import com.eskaya.movie_application.presentation.adapter.MovieListAdapter
import com.eskaya.movie_application.presentation.movie_detail.MovieDetailFragment
import com.eskaya.movie_application.R
import com.eskaya.movie_application.databinding.FragmentSearchMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SearchMovieFragment : Fragment() {
    private lateinit var binding: FragmentSearchMovieBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var movieListAdapter: MovieListAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var timer: Timer? = null
    private lateinit var manager: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listener()
        setUpObservers()
        binding.etSearch.requestFocus()
        manager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT)
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
                if (s.isEmpty()) {
                    movieListAdapter.clear()
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
            //   manager.hideSoftInputFromWindow(it?.windowToken, 0)
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
                        it.results as ArrayList<MovieItem>
                    )
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleSuccess(data: ArrayList<MovieItem>) {
        binding.tvNotFoundMovie.visibility = View.GONE
        movieListAdapter = MovieListAdapter(data,
            object : MovieAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }
            })
        binding.recyclerView.adapter = movieListAdapter
        movieListAdapter.notifyDataSetChanged()
        if (data.isEmpty()) {
            binding.tvNotFoundMovie.visibility = View.VISIBLE
        }
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
}