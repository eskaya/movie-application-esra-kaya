package com.example.movie_application_esra_kaya.presentation.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.FragmentHomeBinding
import com.example.movie_application_esra_kaya.presentation.adapter.ImageSliderAdapter
import com.example.movie_application_esra_kaya.presentation.adapter.TopRatedAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.abs


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter
    val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.getPopularMovieList("popular")
        viewModel.getTopRatedMovieList("top_rated")
        init()
        listener()
        setUpObserversPopularMovies()
        setUpObserverTopRatedMovies()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun listener() {}

    private fun setUpObserversPopularMovies() {
        viewModel.getViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                PopularMovieViewState.Init -> Unit
                is PopularMovieViewState.Error -> handleErrorPopularMovies(it.error)
                is PopularMovieViewState.IsLoading -> handleLoadingPopularMovies(it.isLoading)
                is PopularMovieViewState.Success -> it.data?.let {
                    handleSuccessPopularMovies(
                        it.results
                    )
                }
            }
        }
    }

    private fun setUpObserverTopRatedMovies() {
        viewModel.getTopRatedViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                TopRatedMovieViewState.Init -> Unit
                is TopRatedMovieViewState.Error -> handleErrorTopRatedMovies(it.error)
                is TopRatedMovieViewState.IsLoading -> handleLoadingTopRatedMovies(it.isLoading)
                is TopRatedMovieViewState.Success -> it.data?.let {
                    handleSuccessTopRatedMovies(
                        it.results
                    )
                }
            }
        }
    }


    private fun handleSuccessPopularMovies(data: List<MovieItem>) {
        val runnable = Runnable {
            binding.viewPagerPopular.setCurrentItem(binding.viewPagerPopular.currentItem + 1, true)
        }
        imageSliderAdapter = ImageSliderAdapter(data, binding.viewPagerPopular)
        binding.viewPagerPopular.adapter = imageSliderAdapter
        binding.viewPagerPopular.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000)
            }
        })
    }

    private fun handleSuccessTopRatedMovies(data: List<MovieItem>) {
        topRatedAdapter = TopRatedAdapter(data, binding.viewPagerTopRated)
        binding.viewPagerTopRated.adapter = imageSliderAdapter

        binding.viewPagerTopRated.offscreenPageLimit = 3
        binding.viewPagerTopRated.clipChildren = false
        binding.viewPagerTopRated.clipToPadding = false
        binding.viewPagerTopRated.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(40))
        transform.addTransformer { page, position ->
            var r: Float = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.viewPagerTopRated.setPageTransformer(transform)
    }

    private fun handleLoadingPopularMovies(loading: Boolean) {}
    private fun handleLoadingTopRatedMovies(loading: Boolean) {}

    private fun handleErrorPopularMovies(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun handleErrorTopRatedMovies(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }


}

