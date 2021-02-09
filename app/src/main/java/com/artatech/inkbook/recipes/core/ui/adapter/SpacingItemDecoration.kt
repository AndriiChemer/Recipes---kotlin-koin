package com.artatech.inkbook.recipes.core.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


//val spanCount = 3; // 3 columns
//val spacing = 50; // 50px
//val includeEdge = false;
//recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge));

class SpacingItemDecoration(
    private val spanCount: Int = 1,
    private val spacing: Int = 10,
    private val includeEdge: Boolean = true): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        val column = position % spanCount

        if (includeEdge) {
            outRect.left =
                spacing - column * spacing / spanCount
            outRect.right =
                (column + 1) * spacing / spanCount
            if (position < spanCount) {
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right =
                spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}