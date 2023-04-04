package com.example.movie_application_esra_kaya.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.databinding.ActivityMainBinding
import com.example.movie_application_esra_kaya.presentation.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val fragment = MovieFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}