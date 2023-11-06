package com.example.financemanager.profile.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.example.financemanager.R
import com.example.financemanager.databinding.ViewProfileCardBinding
import com.example.financemanager.profile.card.dto.ProfileCardItem
import com.example.financemanager.profile.card.recycler.ProfileCardAdapter
import com.example.financemanager.profile.card.recycler.ProfileCardItemDecoration

class ProfileCardView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

  var title: String = ""
    set(value) {
      field = value
      viewBinding.profileCardTitleTv.text = value
    }

  var items: List<ProfileCardItem> = emptyList()
    set(value) {
      field = value
      adapter.items = value
      val isItemsVisible = value.isNotEmpty()
      viewBinding.profileCardDivider.isVisible = isItemsVisible
      viewBinding.profileCardRv.isVisible = isItemsVisible
    }

  var onItemClick: ((ProfileCardItem) -> Unit)? = null

  private val viewBinding = ViewProfileCardBinding.inflate(LayoutInflater.from(context), this)

  private val adapter = ProfileCardAdapter(
    context = context,
    onItemClick = { item -> onItemClick?.invoke(item) }
  )

  init {
    background = ContextCompat.getDrawable(context, R.drawable.bg_card)
    orientation = VERTICAL
    updatePadding(
      top = context.resources.getDimensionPixelSize(R.dimen.view_profile_card_padding)
    )

    viewBinding.profileCardRv.adapter = adapter
    val itemDecoration = ProfileCardItemDecoration(
      itemSpacingPx = context.resources.getDimensionPixelSize(R.dimen.view_profile_card_items_spacing)
    )
    viewBinding.profileCardRv.addItemDecoration(itemDecoration)

    context.withStyledAttributes(attrs, R.styleable.ProfileCardView) {
      title = getString(R.styleable.ProfileCardView_title).orEmpty()
    }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (isInEditMode) {
      adapter.items = EditModeItemsStub
    }
  }

  private companion object {

    val EditModeItemsStub = (0..5).map { ProfileCardItem(id = it.toString()) }
  }
}