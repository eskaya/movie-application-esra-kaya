package com.example.movie_application_esra_kaya.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_application_esra_kaya.data.remote.dto.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemPopularMovieBinding

class PopularMovieListAdapter(
    val data: ArrayList<MovieItem>
) : RecyclerView.Adapter<PopularMovieListHistoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMovieListHistoryViewHolder {
        val binding = ListItemPopularMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PopularMovieListHistoryViewHolder(binding, data)
    }

    override fun onBindViewHolder(holder: PopularMovieListHistoryViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class PopularMovieListHistoryViewHolder(
    private val binding: ListItemPopularMovieBinding,
    private val data: ArrayList<MovieItem>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem) {
    }


}
