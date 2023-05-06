package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.ListItemTrailerBinding

class TrailersAdapter(
    val data: List<Trailer>
) : RecyclerView.Adapter<TrailersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailersViewHolder {
        val binding = ListItemTrailerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrailersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class TrailersViewHolder(
    private val binding: ListItemTrailerBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Trailer) {
        binding.tvTitle.text = item.key
    }
}