package com.eskaya.movie_application.presentation.maintenance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eskaya.movie_application.databinding.ActivityMaintenanceBinding

class MaintenanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaintenanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintenanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        listener()
    }

    private fun init() {}

    private fun listener() {
        binding.cardViewButtonWrapper.setOnClickListener {
            finish()
        }
    }
}