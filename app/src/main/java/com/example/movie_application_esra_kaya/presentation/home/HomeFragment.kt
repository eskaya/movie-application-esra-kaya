package com.example.movie_application_esra_kaya.presentation.home

import android.os.Bundle
import android.os.Handler
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
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.FragmentHomeBinding
import com.example.movie_application_esra_kaya.presentation.adapter.*
import com.example.movie_application_esra_kaya.presentation.movie.MovieFragment
import com.example.movie_application_esra_kaya.utils.MovieTypes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var upComingAdapter: UpComingMovieAdapter
    val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.getPopularMovieList(MovieTypes.POPULAR)
        viewModel.getTopRatedMovieList(MovieTypes.TOP_RATED)
        viewModel.getUpComingMovieList(MovieTypes.UPCOMING)
        init()
        listener()
        setUpObserversPopularMovies()
        setUpObserverTopRatedMovies()
        setUpObserverUpComingMovies()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerviewUpComing.layoutManager = layoutManager
    }

    private fun listener() {
        binding.tvSeeMoreTopRated.setOnClickListener {
            val fragment = MovieFragment.newInstance(MovieTypes.TOP_RATED)
            navigate(fragment)
        }
        binding.tvSeeMoreUpcoming.setOnClickListener {
            val fragment = MovieFragment.newInstance(MovieTypes.UPCOMING)
            navigate(fragment)
        }
        binding.tvSeeMorePopular.setOnClickListener {
            val fragment = MovieFragment.newInstance(MovieTypes.POPULAR)
            navigate(fragment)
        }
    }

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

    private fun setUpObserverUpComingMovies() {
        viewModel.getUpComingViewState.observe(
            viewLifecycleOwner
        ) { it ->
            when (it) {
                UpComingMovieViewState.Init -> Unit
                is UpComingMovieViewState.Error -> handleErrorUpComingMovies(it.error)
                is UpComingMovieViewState.IsLoading -> handleLoadingUpComingMovies(it.isLoading)
                is UpComingMovieViewState.Success -> it.data?.let {
                    handleSuccessUpComingMovies(
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

        binding.viewPagerTopRated.offscreenPageLimit = 5
        binding.viewPagerTopRated.clipChildren = false
        binding.viewPagerTopRated.clipToPadding = false
        binding.viewPagerTopRated.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(50))
        transform.addTransformer { page, position ->
            val r: Float = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.viewPagerTopRated.setPageTransformer(transform)
    }

    private fun handleSuccessUpComingMovies(data: List<MovieItem>) {
        upComingAdapter = UpComingMovieAdapter(data)
        binding.recyclerviewUpComing.adapter = upComingAdapter
    }

    private fun handleLoadingPopularMovies(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleLoadingTopRatedMovies(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleLoadingUpComingMovies(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleErrorPopularMovies(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun handleErrorTopRatedMovies(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun handleErrorUpComingMovies(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun navigate(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

}

