package com.example.movie_application_esra_kaya.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemRoundedImageBinding
import com.example.movie_application_esra_kaya.utils.extensions.toFullImageLink


class ImageSliderAdapter(
    val data: List<MovieItem>,
    private val listener: PopularMoviesAdapterListener
) : RecyclerView.Adapter<ImageSliderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSliderViewHolder {
        val binding = ListItemRoundedImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageSliderViewHolder(binding, data, listener)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class ImageSliderViewHolder(
    private val binding: ListItemRoundedImageBinding,
    private var data: List<MovieItem>,
    private val listener: PopularMoviesAdapterListener
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

interface PopularMoviesAdapterListener {
    fun onClickedItem(movieId: Int)
}



