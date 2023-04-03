package com.example.movie_application_esra_kaya.presentation.home

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.request.MovieItem
import com.example.movie_application_esra_kaya.databinding.FragmentMovieListBinding
import com.example.movie_application_esra_kaya.presentation.popular_movie_detail.PopularMovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: HomeViewModel by viewModels()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var popularMovieListAdapter: PopularMovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        init()
        setUpObservers()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun setUpObservers() {
        viewModel.getViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                PopularMovieListViewState.Init -> Unit
                is PopularMovieListViewState.Error -> handleError(it.error)
                is PopularMovieListViewState.IsLoading -> handleLoading(it.isLoading)
                is PopularMovieListViewState.Success -> it.data?.let {
                    handleSuccess(
                        it.results
                    )
                }
            }
        }
    }

    private fun handleSuccess(data: List<MovieItem>) {
        popularMovieListAdapter = PopularMovieListAdapter(data,
            object : PopularMovieAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }

            })
        binding.recyclerView.adapter = popularMovieListAdapter
    }


    private fun handleLoading(loading: Boolean) {

    }

    private fun handleError(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun navigationMovieDetailPage(movieId: Int) {
        val fragment = PopularMovieDetailFragment.newInstance(movieId)

        parentFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }


        /*
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()

         */
    }

}