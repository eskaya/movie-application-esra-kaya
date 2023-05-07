package com.eskaya.movie_application.presentation.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.recyclerview.widget.RecyclerView
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.ListItemTrailerBinding

class TrailersAdapter(
    val data: List<Trailer>,
    private val listener: TrailerAdapterListener
) : RecyclerView.Adapter<TrailersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailersViewHolder {
        val binding = ListItemTrailerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrailersViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = data.size
}

class TrailersViewHolder(
    private val binding: ListItemTrailerBinding,
    private val listener: TrailerAdapterListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        // binding.webView.settings.javaScriptEnabled = true
        //  binding.webView.webChromeClient = object : WebChromeClient() {}
        binding.root.setOnClickListener(this)
    }

    fun bind(item: Trailer) {
        val key = item.key
        /*
        binding.webView.loadData(
            "<iframe " +
                    "width=\"100%\"" +
                    " height=\"100%\" " +
                   // "style=\"background: #000000\" " +
                    "src=\"https://www.youtube.com/embed/$key\" " +
                    "title=\"YouTube video player\" " +
                    "frameborder=\"0\" " +
                    "allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen>" + "</iframe>",
            "text/html",
            "utf-8"
        )

         */
    }

    override fun onClick(v: View?) {
        listener.onClickedItem(adapterPosition)
    }
}

interface TrailerAdapterListener {
    fun onClickedItem(position: Int)
}