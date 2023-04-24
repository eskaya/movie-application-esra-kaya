package com.eskaya.movie_application.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
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
        return MovieListHistoryViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MovieListHistoryViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class MovieListHistoryViewHolder(
    private val binding: ListItemMovieBinding,
    private val listener: MovieAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var item: MovieItem

    init {
        /*
        if(adapterPosition == 0 ){
            val marginLayoutParams = MarginLayoutParams(binding.root.layoutParams)
            marginLayoutParams.setMargins(0, 100, 0, 10)
            binding.root.layoutParams = marginLayoutParams
        }

         */
        binding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.item = item
        binding.tvTitle.text = item.title
        binding.tvContent.text = item.overview
        binding.tvPopularity.text = "${item.popularity.oneDigit} popularity"
        binding.ratingBar.rating = (item.voteAverage / 2).toFloat()
        item.posterPath.let {
            Glide.with(binding.root.context)
                .load(item.posterPath.toFullImageLink())
                .centerCrop()
                .placeholder(R.drawable.ic_cinema_placeholder)
                .into(binding.ivMovie)
        }
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item.id)
    }

}

interface MovieAdapterListener {
    fun onClickedItem(movieId: Int)
}
