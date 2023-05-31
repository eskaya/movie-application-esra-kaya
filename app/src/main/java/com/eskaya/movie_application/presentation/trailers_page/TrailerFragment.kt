package com.eskaya.movie_application.presentation.trailers_page

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.eskaya.movie_application.data.remote.models.models.Trailer
import com.eskaya.movie_application.databinding.FragmentTrailerBinding
import com.eskaya.movie_application.presentation.adapter.YouTubePlayerViewAdapter
import com.eskaya.movie_application.utils.Constants


class TrailerFragment : Fragment() {
    private lateinit var binding: FragmentTrailerBinding
    private lateinit var trailerList: ArrayList<Trailer>
    private var position: Int = 0
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var youtubePlayerViewAdapter: YouTubePlayerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrailerBinding.inflate(layoutInflater)
        init()
        listener()
        return binding.root
    }

    private fun init() {
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun listener() {
        binding.ivClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

            trailerList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(
                    Constants.YOUTUBE_VIDEO_KEY, Trailer::class.java
                ) as ArrayList<Trailer>
            } else {
                @Suppress("DEPRECATION")
                it.getParcelableArrayList(Constants.YOUTUBE_VIDEO_KEY)!!
            }
            position = it.getInt(Constants.POSITION)
            createYoutubePlayerAdapter(trailerList, position)
        }
    }
    /*
    fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): T
    {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.intent.getSerializableExtra(name, clazz)!!
        else
            activity.intent.getSerializableExtra(name) as T
    }

     */

    private fun createYoutubePlayerAdapter(trailerList: ArrayList<Trailer>, position: Int) {
        youtubePlayerViewAdapter = YouTubePlayerViewAdapter(trailerList)
        binding.recyclerView.adapter = youtubePlayerViewAdapter
        binding.recyclerView.scrollToPosition(position)
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
    }

    companion object {
        fun newInstance(trailerList: ArrayList<Trailer>, position: Int) =
            TrailerFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(Constants.YOUTUBE_VIDEO_KEY, trailerList)
                    putInt(Constants.POSITION, position)
                }
            }
    }
}


