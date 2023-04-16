package com.example.movie_application_esra_kaya.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_application_esra_kaya.R
import com.example.movie_application_esra_kaya.data.remote.models.models.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemPopularMovieBinding
import com.example.movie_application_esra_kaya.utils.Constants
import com.example.movie_application_esra_kaya.utils.extensions.oneDigit
import com.example.movie_application_esra_kaya.utils.extensions.toFullImageLink

class MovieListAdapter(
    val data: List<MovieItem>,
    private val listener: PopularMovieAdapterListener
) : RecyclerView.Adapter<PopularMovieListHistoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMovieListHistoryViewHolder {
        val binding = ListItemPopularMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularMovieListHistoryViewHolder(binding, data, listener)
    }

    override fun onBindViewHolder(holder: PopularMovieListHistoryViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class PopularMovieListHistoryViewHolder(
    private val binding: ListItemPopularMovieBinding,
    private val data: List<MovieItem>,
    private val listener: PopularMovieAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var item: MovieItem

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.item = item
        binding.tvTitle.text = item.title
        binding.tvContent.text = item.overview
        binding.tvPopularity.text = item.popularity.oneDigit + " popularity"
        binding.ratingBar.rating = (item.voteAverage/2).toFloat()
        Glide.with(binding.root.context)
            .load( item.posterPath.toFullImageLink())
            .centerCrop()
            .placeholder(R.drawable.ic_cinema_placeholder)
            .into(binding.ivMovie)
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item.id)
    }

}

interface PopularMovieAdapterListener {
    fun onClickedItem(movieId: Int)
}