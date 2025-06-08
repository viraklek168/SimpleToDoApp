package com.virak.simpletodoapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val spacePx: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.bottom = spacePx
        // Optionally add space to other sides
        // outRect.top = spacePx
        // outRect.left = spacePx
        // outRect.right = spacePx
    }
}