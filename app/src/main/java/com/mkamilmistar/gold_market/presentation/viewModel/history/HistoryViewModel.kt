package com.mkamilmistar.gold_market.presentation.viewModel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult

class HistoryViewModel(private val repository: PurchaseRepository): ViewModel()  {
  private var _historyLiveData = MutableLiveData<EventResult<List<Purchase>>>(EventResult.Idle)
  val historyLiveData: LiveData<EventResult<List<Purchase>>>
    get() = _historyLiveData

  fun start() {
    updateData()
  }

  private fun updateData() {

  }
}
