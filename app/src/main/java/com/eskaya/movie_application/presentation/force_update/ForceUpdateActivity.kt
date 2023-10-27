package com.eskaya.movie_application.presentation.force_update

import android.R
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.eskaya.movie_application.databinding.ActivityForceUpdateBinding


class ForceUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForceUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForceUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}