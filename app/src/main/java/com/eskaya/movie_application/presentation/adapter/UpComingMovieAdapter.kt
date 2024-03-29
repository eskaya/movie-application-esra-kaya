package com.eskaya.movie_application.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.databinding.ListItemUpcomingMovieCardBinding
import com.eskaya.movie_application.utils.extensions.toFullImageLink


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
        return UpComingMovieViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UpComingMovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}

class UpComingMovieViewHolder(
    private val binding: ListItemUpcomingMovieCardBinding,
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
        binding.ratingBar.rating = (item.voteAverage / 2).toFloat()
        if(item.backdropPath != null) {
            Glide.with(binding.root.context)
                .load(item.backdropPath.toFullImageLink())
                .centerCrop()
                .placeholder(R.drawable.ic_cinema_placeholder)
                .into(binding.ivMoviePoster)
        }
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item)
    }
}

interface UpComingMoviesAdapterListener {
    fun onClickedItem(movieItem: MovieItem)
}



