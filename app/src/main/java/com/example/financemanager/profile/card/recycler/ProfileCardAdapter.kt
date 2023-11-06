package com.example.financemanager.profile.card.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.financemanager.databinding.ItemProfileCardBinding
import com.example.financemanager.profile.card.recycler.ProfileCardAdapter.Holder
import com.example.financemanager.profile.card.dto.ProfileCardItem

class ProfileCardAdapter(
  private val context: Context,
  private val onItemClick: (ProfileCardItem) -> Unit = {}
) : RecyclerView.Adapter<Holder>() {

  var items: List<ProfileCardItem> = emptyList()
    set(value) {
      field = value
      val diffUtilCallback = DiffUtilCallback(field, value)
      val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
      diffUtilResult.dispatchUpdatesTo(this)
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    val itemViewBinding = ItemProfileCardBinding.inflate(
      LayoutInflater.from(context),
      parent,
      false
    )
    return Holder(itemViewBinding)
  }

  override fun onBindViewHolder(holder: Holder, position: Int) {
    val item = items.getOrNull(position)
    item?.let(holder::bind)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  inner class Holder(
    private val viewBinding: ItemProfileCardBinding
  ) : ViewHolder(viewBinding.root) {

    private var item: ProfileCardItem? = null

    init {
      viewBinding.root.setOnClickListener { item?.let(onItemClick) }
    }

    fun bind(item: ProfileCardItem) {
      viewBinding.profileCardItemIv.setImageResource(item.iconRes)
    }
  }

  private class DiffUtilCallback(
    private val oldList: List<ProfileCardItem>,
    private val newList: List<ProfileCardItem>
  ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      val oldItem = oldList.getOrNull(oldItemPosition)
      val newItem = newList.getOrNull(newItemPosition)
      return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      val oldItem = oldList.getOrNull(oldItemPosition)
      val newItem = newList.getOrNull(newItemPosition)
      return oldItem?.iconRes == newItem?.iconRes
    }
  }
}