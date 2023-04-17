package com.example.movie_application_esra_kaya.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemTopRatedBinding
import com.example.movie_application_esra_kaya.utils.extensions.toFullImageLink


class TopRatedAdapter(
    val data: List<MovieItem>,
    private val viewPager2: ViewPager2
    //  private val listener: PopularMovieAdapterListener
) : RecyclerView.Adapter<TopRatedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedViewHolder {
        val binding = ListItemTopRatedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopRatedViewHolder(binding, data, viewPager2)
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class TopRatedViewHolder(
    private val binding: ListItemTopRatedBinding,
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



