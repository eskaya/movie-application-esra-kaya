package com.eskaya.movie_application.presentation.trailers_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.FragmentTrailerBinding
import com.eskaya.movie_application.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class TrailerFragment : Fragment() {
    private lateinit var binding: FragmentTrailerBinding
    private lateinit var key: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrailerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { key = it.getString(Constants.YOUTUBE_VIDEO_KEY).toString() }
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                //TODO --> bu sayfaya trailersDto yu gönder, ardından videoId olarak dizi[position].key
                val videoId = key
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    companion object {
        fun newInstance(trailerList: ArrayList<Trailer>, position: Int) =
            TrailerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(Constants.YOUTUBE_VIDEO_KEY, trailerList)
                    putInt(Constants.POSITION, position)
                }
            }
    }
}


