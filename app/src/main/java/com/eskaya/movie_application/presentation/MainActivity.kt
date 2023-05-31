package com.eskaya.movie_application.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eskaya.movie_application.R
import com.eskaya.movie_application.databinding.ActivityMainBinding

import com.eskaya.movie_application.presentation.dashboard.DashboardFragment
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
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }
}