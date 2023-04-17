package com.example.movie_application_esra_kaya.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemUpcomingMovieCardBinding
import com.example.movie_application_esra_kaya.utils.extensions.toFullImageLink


class UpComingMovieAdapter(
    val data: List<MovieItem>,
    private val listener: UpComingMoviesAdapterListener
) : RecyclerView.Adapter<UpComingMovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpComingMovieViewHolder {
        val binding = ListItemUpcomingMovieCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UpComingMovieViewHolder(binding, data, listener)
    }

    override fun onBindViewHolder(holder: UpComingMovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class UpComingMovieViewHolder(
    private val binding: ListItemUpcomingMovieCardBinding,
    private var data: List<MovieItem>,
    private val listener: UpComingMoviesAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var item: MovieItem

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.item = item
        binding.tvTitle.text = item.title
        binding.tvOriginalLngValue.text = item.originalLanguage
        binding.ratingBar.rating = (item.voteAverage/2).toFloat()
        Glide.with(binding.root.context)
            .load(item.backdropPath.toFullImageLink())
            .centerCrop()
            .placeholder(R.drawable.ic_cinema_placeholder)
            .into(binding.ivMoviePoster)
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item.id)
    }
}

interface UpComingMoviesAdapterListener {
    fun onClickedItem(movieId: Int)
}



