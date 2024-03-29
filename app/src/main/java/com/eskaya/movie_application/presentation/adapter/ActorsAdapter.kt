package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eskaya.movie_application.R
import com.eskaya.movie_application.data.remote.models.models.Cast
import com.eskaya.movie_application.databinding.ListItemActorsBinding
import com.eskaya.movie_application.utils.extensions.toFullImageLink

class ActorsAdapter(
    val data: List<Cast>
) : RecyclerView.Adapter<ActorsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorsViewHolder {
        val binding = ListItemActorsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class ActorsViewHolder(
    private val binding: ListItemActorsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Cast) {
        if (item.profilePath != null) {
            Glide.with(binding.root.context)
                .load(item.profilePath.toFullImageLink())
                .centerCrop()
                .placeholder(R.drawable.ic_cinema_placeholder)
                .into(binding.ivActor)
        } else {
            // Test!
            binding.ivActor.setImageResource(R.drawable.ic_question_mark)
        }
    }
}