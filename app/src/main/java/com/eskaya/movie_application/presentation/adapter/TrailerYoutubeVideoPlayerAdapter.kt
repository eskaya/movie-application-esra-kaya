package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.ListItemYoutubeVideoPlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailerYoutubeVideoPlayerAdapter(
    val data: List<Trailer>,
    val position: Int
) : RecyclerView.Adapter<TrailerYoutubeVideoPlayerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailerYoutubeVideoPlayerViewHolder {
        val binding = ListItemYoutubeVideoPlayerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrailerYoutubeVideoPlayerViewHolder(binding, data, position)
    }

    override fun onBindViewHolder(holder: TrailerYoutubeVideoPlayerViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class TrailerYoutubeVideoPlayerViewHolder(
    private var binding: ListItemYoutubeVideoPlayerBinding,
    private val data: List<Trailer>,
    private val position: Int
) : RecyclerView.ViewHolder(binding.root) {

    private val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView

    init {
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = data[position].key
                youTubePlayer.loadVideo(videoId, 0f)
                println("videoId$videoId")
            }
        })
    }

    fun bind(item: Trailer) {
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = data[adapterPosition].key
                println(adapterPosition)
                youTubePlayer.loadVideo(videoId, 0f)
                println("videoId$videoId")
            }
        })
    }
}