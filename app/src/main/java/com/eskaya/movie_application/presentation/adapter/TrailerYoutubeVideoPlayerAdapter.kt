package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.ListItemYoutubeVideoPlayerBinding

class TrailerYoutubeVideoPlayerAdapter(
    val data: List<Trailer>
) : RecyclerView.Adapter<TrailerYoutubeVideoPlayerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailerYoutubeVideoPlayerViewHolder {
        val binding = ListItemYoutubeVideoPlayerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrailerYoutubeVideoPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailerYoutubeVideoPlayerViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class TrailerYoutubeVideoPlayerViewHolder(
    private val binding: ListItemYoutubeVideoPlayerBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Trailer) {

    }
}