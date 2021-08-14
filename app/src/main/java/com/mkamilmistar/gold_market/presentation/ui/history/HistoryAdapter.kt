package com.mkamilmistar.gold_market.presentation.ui.history

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.databinding.HistoryListItemBinding
import com.mkamilmistar.gold_market.utils.Utils

class HistoryAdapter(private val onClickItemListener: OnClickItemListener) :
  RecyclerView.Adapter<HistoryAdapter.TodoViewHolder>() {

  var histories: MutableList<Purchase> = mutableListOf()

  class TodoViewHolder(val binding: HistoryListItemBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
    val binding: HistoryListItemBinding =
      HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return TodoViewHolder(binding)
  }

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
    with(holder.binding) {
      with(histories[position]) {

        historyDateText.text = this.purchaseDate

        if (this.purchaseType == 0) {
          productNameText.text = "Buy $qtyInGram /gr"
          pictureProduct.setImageResource(R.drawable.gold)
          priceProductText.text = "+${Utils.currencyFormatter(this.price)}"
          priceProductText.setTextColor(Color.parseColor("#1EC15F"))
        } else {
          productNameText.text = "Sell $qtyInGram /gr"
          pictureProduct.setImageResource(R.drawable.bronze)
          priceProductText.text = "-${Utils.currencyFormatter(this.price)}"
          priceProductText.setTextColor(Color.parseColor("#FF5B37"))
        }

        cardItemHistory.setOnClickListener {
          onClickItemListener.onClickItem(position)
        }
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  fun updateData(purchaseHistories: CustomerWithPurchases) {
    this.histories.clear()
    this.histories.addAll(purchaseHistories.purchases)
    notifyDataSetChanged()
  }

  interface OnClickItemListener {
    fun onClickItem(position: Int)
  }

  override fun getItemCount(): Int = histories.size
}
