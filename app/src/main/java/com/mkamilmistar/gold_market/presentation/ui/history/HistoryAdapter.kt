package com.mkamilmistar.gold_market.presentation.ui.history

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import com.mkamilmistar.gold_market.databinding.HistoryListItemBinding
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.formatDate

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

        historyDateText.text = formatDate(this.purchaseDate)
        val qty = purchaseDetails.first().quantityInGram
        val pocketName = purchaseDetails.first().pocket.pocketName

        if (this.purchaseType.toInt() == 0) {
          productNameText.text = "$pocketName $qty /gr"
          pictureProduct.setImageResource(R.drawable.gold)
          priceProductText.text = "+${purchaseDetails.first().product.let { Utils.currencyFormatter(it.productPriceSell * qty) }}"
          priceProductText.setTextColor(Color.parseColor("#1EC15F"))
        } else {
          productNameText.text = "$pocketName $qty /gr"
          pictureProduct.setImageResource(R.drawable.bronze)
          priceProductText.text = "-${purchaseDetails.first().product.let { Utils.currencyFormatter(it.productPriceBuy * qty) }}"
          priceProductText.setTextColor(Color.parseColor("#FF5B37"))
        }

        cardItemHistory.setOnClickListener {
          onClickItemListener.onClickItem(position)
        }
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  fun updateData(purchaseHistories: List<Purchase>?) {
    this.histories.clear()
    if (purchaseHistories != null) {
      this.histories.addAll(purchaseHistories)
    }
    notifyDataSetChanged()
  }

  interface OnClickItemListener {
    fun onClickItem(position: Int)
  }

  override fun getItemCount(): Int = histories.size
}
