package com.example.movie_application_esra_kaya.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemRoundedImageBinding
import com.example.movie_application_esra_kaya.utils.extensions.toFullImageLink


class ImageSliderAdapter(
    val data: List<MovieItem>,
    val viewPager2: ViewPager2
    //  private val listener: PopularMovieAdapterListener
) : RecyclerView.Adapter<ImageSliderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageSliderViewHolder {
        val binding = ListItemRoundedImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ImageSliderViewHolder(binding, data, viewPager2)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class ImageSliderViewHolder(
    private val binding: ListItemRoundedImageBinding,
    private var data: List<MovieItem>,
    var viewPager2: ViewPager2
    //  private val listener: PopularMovieAdapterListener
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var item: MovieItem

    fun bind(item: MovieItem) {
        //  binding.imageView.setImageResource(item.posterPath.toFullImageLink())

        Glide.with(binding.root.context)
            .load(item.backdropPath.toFullImageLink())
            .centerCrop()
            .placeholder(R.drawable.ic_cinema_placeholder)
            .into(binding.imageView)

    }
}



