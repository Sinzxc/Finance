package com.example.financemanager.profile.card.recycler

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State

class ProfileCardItemDecoration(
  @Px private val itemSpacingPx: Int
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
    super.getItemOffsets(outRect, view, parent, state)
    val itemPosition = parent.getChildAdapterPosition(view)
    val itemsCount = (parent.adapter as ProfileCardAdapter).items.size
    val shouldDrawSpacing = itemsCount > 1 && itemPosition < itemsCount - 1
    if (shouldDrawSpacing) {
      outRect.right = itemSpacingPx
    }
  }
}