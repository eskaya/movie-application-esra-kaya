package com.eskaya.movie_application.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.Genre
import com.eskaya.movie_application.databinding.ListItemGenresBinding

class GenresAdapter(
    val data: List<Genre>
) : RecyclerView.Adapter<GenresViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresViewHolder {
        val binding = ListItemGenresBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GenresViewHolder(binding, data)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) =
        holder.bind(data[position])
    override fun getItemCount(): Int = data.size
}

class GenresViewHolder(
    private val binding: ListItemGenresBinding,
    private val data: List<Genre>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Genre) {
        binding.tvGenres.text = item.name
    }
}