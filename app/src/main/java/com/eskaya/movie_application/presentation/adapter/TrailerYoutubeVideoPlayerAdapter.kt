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
    val pos: Int
) : RecyclerView.Adapter<TrailerYoutubeVideoPlayerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailerYoutubeVideoPlayerViewHolder {
        val binding = ListItemYoutubeVideoPlayerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrailerYoutubeVideoPlayerViewHolder(binding, data, pos)
    }

    override fun onBindViewHolder(holder: TrailerYoutubeVideoPlayerViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class TrailerYoutubeVideoPlayerViewHolder(
    private var binding: ListItemYoutubeVideoPlayerBinding,
    private val data: List<Trailer>,
    private val pos: Int
) : RecyclerView.ViewHolder(binding.root) {


    //TODO --> video oynamaya başladıktn sonra diğer video oynamaya başlıyor. Sayfadan çıkınca video kapanmıyor
    fun bind(item: Trailer) {
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = data[adapterPosition].key
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
}