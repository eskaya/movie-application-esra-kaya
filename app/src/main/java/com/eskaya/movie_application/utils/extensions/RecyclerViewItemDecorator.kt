package com.eskaya.movie_application.utils.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewItemDecorator(
    private val spaceBetween: Int,
    private val paddingStart: Int = spaceBetween,
    private val paddingTop: Int = spaceBetween,
    private val paddingEnd: Int = spaceBetween,
    private val paddingBottom: Int = spaceBetween
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
                    outRect.set(paddingStart, paddingTop, spaceBetween, paddingBottom)
                }
                position < parent.adapter!!.itemCount - 1 -> {
                    outRect.set(0, paddingTop, spaceBetween, paddingBottom)
                }
                else -> {
                    outRect.set(0, paddingTop, paddingEnd, paddingBottom)
                }
            }
        } else {
            when {
                position == 0 -> {
                    outRect.set(paddingStart, paddingTop, paddingEnd, paddingBottom)
                }
                position < parent.adapter!!.itemCount - 1 -> {
                    outRect.set(paddingStart, 0, paddingEnd, spaceBetween)
                }
                else -> {
                    outRect.set(paddingStart, 0, paddingEnd, paddingBottom)
                }
            }
        }
    }
}

