package com.eskaya.movie_application.presentation.trailers_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eskaya.movie_application.databinding.FragmentTrailerBinding
import com.eskaya.movie_application.presentation.movie_detail.MovieDetailFragment
import com.eskaya.movie_application.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class TrailerFragment : Fragment() {
    private lateinit var binding: FragmentTrailerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrailerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "LgZ2MDuJvhc"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    companion object {
        fun newInstance(key: String) =
            TrailerFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.YOUTUBE_VIDEO_KEY, key)
                }
            }
    }
}