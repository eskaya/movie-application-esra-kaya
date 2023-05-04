package com.eskaya.movie_application.presentation.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.databinding.FragmentDashboardBinding
import com.eskaya.movie_application.presentation.adapter.*
import com.eskaya.movie_application.presentation.movie_list.MovieListFragment
import com.eskaya.movie_application.presentation.movie_detail.MovieDetailFragment
import com.eskaya.movie_application.presentation.search.SearchMovieFragment
import com.eskaya.movie_application.utils.MovieTypes
import com.eskaya.movie_application.utils.extensions.RecyclerViewItemDecorator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter
    private lateinit var upComingAdapter: UpComingMovieAdapter
    val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        viewModel.fetchUsers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listener()
        setUpObserver()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setUpObserver() {
        viewModel.getUiState().observe(this) {
            when (it) {
                MovieViewState.Init -> Unit
                is MovieViewState.Success -> {
                    binding.containerProgress.visibility = View.GONE
                    it.data?.get(0)?.let { it1 -> handleSuccessPopularMovies(it1.results) }
                    it.data?.get(1)?.let { it1 -> handleSuccessUpComingMovies(it1.results) }
                    it.data?.get(2)?.let { it1 -> handleSuccessTopRatedMovies(it1.results) }
                }
                is MovieViewState.IsLoading -> {
                    // binding.containerProgress.visibility = View.VISIBLE
                    handleLoadingPopularMovies(it.isLoading)
                }
                is MovieViewState.Error -> {
                    binding.containerProgress.visibility = View.GONE
                    //  Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                    handleErrorPopularMovies(it.error)
                }
            }
        }
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.recyclerviewUpComing.layoutManager = layoutManager
        val actorsDecoration = RecyclerViewItemDecorator(
            spaceBetween = 16,
            spaceStart = 40,
        )
        binding.recyclerviewUpComing.addItemDecoration(actorsDecoration)
    }

    private fun listener() {
        binding.tvSeeMoreTopRated.setOnClickListener {
            val fragment = MovieListFragment.newInstance(MovieTypes.TOP_RATED)
            navigate(fragment)
        }
        binding.tvSeeMoreUpcoming.setOnClickListener {
            val fragment = MovieListFragment.newInstance(MovieTypes.UPCOMING)
            navigate(fragment)
        }
        binding.tvSeeMorePopular.setOnClickListener {
            val fragment = MovieListFragment.newInstance(MovieTypes.POPULAR)
            navigate(fragment)
        }
        binding.cvSearch.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.frameLayout, SearchMovieFragment())
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }

    private fun handleSuccessPopularMovies(data: List<MovieItem>) {
        val runnable = Runnable {
            binding.viewPagerPopular.setCurrentItem(binding.viewPagerPopular.currentItem + 1, true)
        }
        popularMoviesAdapter = PopularMoviesAdapter(data,
            object : PopularMoviesAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }
            }
        )
        binding.viewPagerPopular.adapter = popularMoviesAdapter
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
        topRatedAdapter = TopRatedAdapter(data,
            object : TopRatedMovieAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }
            }
        )
        binding.viewPagerTopRated.adapter = topRatedAdapter
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
        upComingAdapter = UpComingMovieAdapter(data,
            object : UpComingMoviesAdapterListener {
                override fun onClickedItem(movieId: Int) {
                    navigationMovieDetailPage(movieId)
                }
            }
        )
        binding.recyclerviewUpComing.adapter = upComingAdapter
    }

    private fun handleLoadingPopularMovies(loading: Boolean) {
        binding.containerProgress.isVisible = loading
    }

    private fun handleErrorPopularMovies(error: Any) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }


    private fun navigate(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
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