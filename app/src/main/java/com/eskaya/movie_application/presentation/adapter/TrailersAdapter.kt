package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.ListItemTrailerBinding
import com.eskaya.movie_application.utils.extensions.toFullImageLink

class TrailersAdapter(
    val data: List<Trailer>,
    private val listener: TrailerAdapterListener
) : RecyclerView.Adapter<TrailersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailersViewHolder {
        val binding = ListItemTrailerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrailersViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class TrailersViewHolder(
    private val binding: ListItemTrailerBinding,
    private val listener: TrailerAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: Trailer) {
        val key = item.key
        Glide.with(binding.root.context)
            .load("https://i.ytimg.com/vi/$key/maxresdefault.jpg")
            .into(binding.ivYoutube)
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(adapterPosition)
    }
}

interface TrailerAdapterListener {
    fun onClickedItem(position: Int)
}