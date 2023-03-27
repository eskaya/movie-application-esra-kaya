package com.example.movie_application_esra_kaya.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_application_esra_kaya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}