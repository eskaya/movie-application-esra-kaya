package com.eskaya.movie_application.presentation.trailers_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.FragmentTrailerBinding
import com.eskaya.movie_application.presentation.adapter.TrailerYoutubeVideoPlayerAdapter
import com.eskaya.movie_application.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlin.properties.Delegates


class TrailerFragment : Fragment() {
    private lateinit var binding: FragmentTrailerBinding
    private lateinit var trailerList: ArrayList<Trailer>
    private var position by Delegates.notNull<Int>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var trailerYoutubeVideoPlayerAdapter: TrailerYoutubeVideoPlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrailerBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            trailerList = it.getParcelableArrayList(Constants.YOUTUBE_VIDEO_KEY)!!
            position = it.getInt(Constants.POSITION)
            createYoutubePlayerAdapter(trailerList, position)
        }
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = trailerList[position].key
                youTubePlayer.loadVideo(videoId, 0f)
                Toast.makeText(context, trailerList[position].key, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createYoutubePlayerAdapter(trailerList: ArrayList<Trailer>, position: Int) {
        trailerYoutubeVideoPlayerAdapter = TrailerYoutubeVideoPlayerAdapter(trailerList, position)
        binding.recyclerView.adapter = trailerYoutubeVideoPlayerAdapter
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


