package com.dev.playground.presentation.ui.feed

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dev.playground.presentation.R

class FeedItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val size = parent.adapter?.itemCount ?: return

        if (position != size - 1) {
            outRect.bottom = view.context.resources.getDimensionPixelSize(R.dimen.spacing_32)
        }
    }

}