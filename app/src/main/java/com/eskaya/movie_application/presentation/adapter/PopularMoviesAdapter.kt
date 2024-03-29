package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.MovieItem
import com.eskaya.movie_application.databinding.ListItemRoundedImageBinding
import com.eskaya.movie_application.utils.extensions.toFullImageLink

class PopularMoviesAdapter(
    val data: List<MovieItem>,
    private val listener: PopularMoviesAdapterListener
) : RecyclerView.Adapter<PopularMoviesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMoviesViewHolder {
        val binding = ListItemRoundedImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularMoviesViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class PopularMoviesViewHolder(
    private val binding: ListItemRoundedImageBinding,
    private val listener: PopularMoviesAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var item: MovieItem

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(item: MovieItem) {
        this.item = item
        if(item.backdropPath != null) {
            Glide.with(binding.root.context)
                .load(item.backdropPath.toFullImageLink())
                .centerCrop()
                .placeholder(R.drawable.ic_cinema_placeholder)
                //  .apply(RequestOptions().override(2000, 1000))
                .into(binding.imageView)
        }
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(item)
    }
}

interface PopularMoviesAdapterListener {
    fun onClickedItem(movieItem: MovieItem)
}



