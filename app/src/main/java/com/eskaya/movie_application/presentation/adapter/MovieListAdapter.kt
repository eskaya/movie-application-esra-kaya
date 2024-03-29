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
    val data: ArrayList<MovieItem>,
    private val listener: MovieAdapterListener
) : RecyclerView.Adapter<MovieListHistoryViewHolder>() {

    fun clear() {
        val size: Int = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }

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
        val context = binding.root.context
        this.item = item
        binding.tvTitle.text = item.title
        binding.tvContent.text = item.overview
        binding.tvPopularity.text =
            context.resources.getString(R.string.popularity, item.popularity.oneDigit)
        binding.ratingBar.rating = (item.voteAverage / 2).toFloat()

        val str = item.releaseDate
        if(!str.isNullOrEmpty()){
            val parts = str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            binding.tvDate.text = parts[0]
        }else{
            binding.tvDate.text = "-"
        }
        if (item.posterPath != null) {
            Glide.with(context)
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
