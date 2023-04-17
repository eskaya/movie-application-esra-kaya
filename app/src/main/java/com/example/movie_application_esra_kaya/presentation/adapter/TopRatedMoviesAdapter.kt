package com.example.movie_application_esra_kaya.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemTopRatedBinding
import com.example.movie_application_esra_kaya.utils.extensions.toFullImageLink


class TopRatedAdapter(
    val data: List<MovieItem>,
    private val listener: TopRatedMovieAdapterListener
) : RecyclerView.Adapter<TopRatedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedViewHolder {
        val binding = ListItemTopRatedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopRatedViewHolder(binding, data, listener)
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class TopRatedViewHolder(
    private val binding: ListItemTopRatedBinding,
    private var data: List<MovieItem>,
    private val listener: TopRatedMovieAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var item: MovieItem

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.item = item
        Glide.with(binding.root.context)
            .load(item.backdropPath.toFullImageLink())
            .centerCrop()
            .placeholder(R.drawable.ic_cinema_placeholder)
            .into(binding.imageView)
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item.id)
    }
}

interface TopRatedMovieAdapterListener {
    fun onClickedItem(movieId: Int)
}



