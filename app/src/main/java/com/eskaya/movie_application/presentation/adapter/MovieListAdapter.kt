package com.eskaya.movie_application.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.databinding.ListItemMovieBinding
import com.eskaya.movie_application.utils.extensions.oneDigit
import com.eskaya.movie_application.utils.extensions.toFullImageLink

class MovieListAdapter(
    val data: List<MovieItem>,
    private val listener: MovieAdapterListener
) : RecyclerView.Adapter<MovieListHistoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListHistoryViewHolder {
        val binding = ListItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieListHistoryViewHolder(binding, listener, data)
    }

    override fun onBindViewHolder(holder: MovieListHistoryViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class MovieListHistoryViewHolder(
    private val binding: ListItemMovieBinding,
    private val listener: MovieAdapterListener,
    val data: List<MovieItem>,
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var item: MovieItem

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.item = item
        binding.tvTitle.text = item.title
        binding.tvContent.text = item.overview
        binding.tvPopularity.text = "${item.popularity.oneDigit} popularity"
        binding.ratingBar.rating = (item.voteAverage / 2).toFloat()


        val str = item.releaseDate
        val parts = str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        binding.tvDate.text = parts[0]
        item.posterPath.let {
            Glide.with(binding.root.context)
                .load(item.posterPath.toFullImageLink())
                .centerCrop()
                .placeholder(R.drawable.ic_cinema_placeholder)
                .into(binding.ivMovie)
        }
        val layoutParams = binding.root.layoutParams as RecyclerView.LayoutParams
        when (adapterPosition) {
            0 -> {
                layoutParams.setMargins(0, 36, 0, 36)
            }
            (data.size) - 1 -> {
                layoutParams.setMargins(0, 0, 0, 236)
            }
            else -> {
                layoutParams.setMargins(0, 0, 0, 36)
            }
        }
        binding.root.layoutParams = layoutParams
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item.id)
    }

}

interface MovieAdapterListener {
    fun onClickedItem(movieId: Int)
}
