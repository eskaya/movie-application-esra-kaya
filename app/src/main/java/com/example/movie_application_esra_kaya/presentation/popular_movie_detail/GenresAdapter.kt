package com.example.movie_application_esra_kaya.presentation.popular_movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_application_esra_kaya.data.remote.models.request.MovieItem
import com.example.movie_application_esra_kaya.databinding.ListItemGenresBinding

class GenresAdapter(
    val data: List<MovieItem>
) : RecyclerView.Adapter<GenresViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresViewHolder {
        val binding = ListItemGenresBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = 3
}

class GenresViewHolder(
    private val binding: ListItemGenresBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem) {
    }


}