package com.eskaya.movie_application.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.eskaya.movie_application.R
import com.eskaya.movie_application.databinding.ActivityMainBinding

import com.eskaya.movie_application.presentation.dashboard.DashboardFragment
import com.eskaya.movie_application.presentation.movie_detail.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var movieId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getStringExtra("movieId") as Nothing?
        init()
    }

    private fun init() {
        if (movieId != null) {
            val fragment = MovieDetailFragment.newInstance(movieId!!.toInt())
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
        } else {
            val fragment = DashboardFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.commit()
        }
    }
}