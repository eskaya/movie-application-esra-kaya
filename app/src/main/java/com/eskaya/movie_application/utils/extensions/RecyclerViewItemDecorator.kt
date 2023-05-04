package com.eskaya.movie_application.utils.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewItemDecorator(
    private val spaceBetween: Int,
    private val spaceStart: Int = spaceBetween,
    private val spaceTop: Int = spaceBetween,
    private val spaceEnd: Int = spaceBetween,
    private val spaceBottom: Int = spaceBetween
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val orientation = when (val layoutManager = parent.layoutManager) {
            is LinearLayoutManager -> {
                layoutManager.orientation
            }
            is GridLayoutManager -> {
                layoutManager.orientation
            }
            else -> {
                RecyclerView.HORIZONTAL
            }
        }
        if (orientation == RecyclerView.HORIZONTAL) {
            when {
                position == 0 -> {
                    outRect.set(spaceStart, spaceTop, spaceBetween, spaceBottom)
                }
                position < parent.adapter!!.itemCount - 1 -> {
                    outRect.set(0, spaceTop, spaceBetween, spaceBottom)
                }
                else -> {
                    outRect.set(0, spaceTop, spaceEnd, spaceBottom)
                }
            }
        } else {
            when {
                position == 0 -> {
                    outRect.set(spaceStart, spaceTop, spaceEnd, spaceBottom)
                }
                position < parent.adapter!!.itemCount - 1 -> {
                    outRect.set(spaceStart, 0, spaceEnd, spaceBetween)
                }
                else -> {
                    outRect.set(spaceStart, 0, spaceEnd, spaceBottom)
                }
            }
        }
    }
}

