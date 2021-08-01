package com.mkamilmistar.gold_market.ui.pocket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkamilmistar.gold_market.data.model.Pocket
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.databinding.PocketListItemBinding

class PocketAdapter(private val onClickItemListener: OnClickItemListener) :
  RecyclerView.Adapter<PocketAdapter.TodoViewHolder>() {

  var pockets: MutableList<Pocket> = mutableListOf()

  class TodoViewHolder(val binding: PocketListItemBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
    val binding: PocketListItemBinding =
      PocketListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return TodoViewHolder(binding)
  }

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
    with(holder.binding) {
      with(pockets[position]) {
        pocketNameText.text = pocketName
        pocketQtyText.text = "$pocketQty /gr"

        cardItemPocket.setOnClickListener {
          onClickItemListener.onClickItem(position)
        }

        btnDeletePocket.setOnClickListener {
          onClickItemListener.deleteItem(position)
        }

        btnEditPocket.setOnClickListener {
          onClickItemListener.editItem(position)
        }
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  fun updateData(pockets: List<Pocket>) {
    this.pockets.clear()
    this.pockets.addAll(pockets)
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int = pockets.size

  interface OnClickItemListener {
    fun onClickItem(position: Int)
    fun deleteItem(position: Int)
    fun editItem(position: Int)
  }

}
