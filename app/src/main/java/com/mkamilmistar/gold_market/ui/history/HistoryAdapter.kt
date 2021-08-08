package com.mkamilmistar.gold_market.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.databinding.HistoryListItemBinding
import com.mkamilmistar.gold_market.utils.Utils

class HistoryAdapter(private val onClickItemListener: OnClickItemListener) :
  RecyclerView.Adapter<HistoryAdapter.TodoViewHolder>(){

  var histories: MutableList<Purchase> = mutableListOf()
  class TodoViewHolder(val binding: HistoryListItemBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
    val binding: HistoryListItemBinding =
      HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return TodoViewHolder(binding)
  }

  override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
    with(holder.binding) {
      with(histories[position]) {
        productNameText.text = this.id
        priceProductText.text = Utils.currencyFormatter(this.price)
        historyDateText.text = this.purchaseDate

        cardItemHistory.setOnClickListener {
          onClickItemListener.onClickItem(position)
        }
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  fun updateData(histories: List<Purchase>) {
    this.histories.clear()
    this.histories.addAll(histories)
    notifyDataSetChanged()
  }

  interface OnClickItemListener {
    fun onClickItem(position: Int)
  }

  override fun getItemCount(): Int = histories.size
}
