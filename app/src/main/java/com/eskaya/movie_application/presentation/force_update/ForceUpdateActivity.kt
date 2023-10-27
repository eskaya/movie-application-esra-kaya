package com.eskaya.movie_application.presentation.force_update

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eskaya.movie_application.databinding.ActivityForceUpdateBinding


class ForceUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForceUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForceUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
    }

    private fun listener() {
        binding.cvUpdateApp.setOnClickListener {
            val appPackageName = "com.eskaya.movie_application"
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                //Google Play uygulaması yoksa
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }
}