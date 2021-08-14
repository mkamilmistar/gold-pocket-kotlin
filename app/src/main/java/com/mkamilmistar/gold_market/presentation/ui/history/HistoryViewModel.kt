package com.mkamilmistar.gold_market.presentation.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HistoryViewModel(private val repository: PurchaseRepository): ViewModel() {
  private var _historyLiveData = MutableLiveData<EventResult<List<Purchase>>>(EventResult.Idle)
  val historyLiveData: LiveData<EventResult<List<Purchase>>>
    get() = _historyLiveData

  fun start() {
    updateData()
  }

  private fun getHistoryRepository() = repository.findAllPurchase()

  private fun updateData() {
    _historyLiveData.value = EventResult.Loading
    try {
      val history: List<Purchase> = getHistoryRepository()
      _historyLiveData.value = EventResult.Success(history)
    } catch (e: Exception) {
      _historyLiveData.value = EventResult.Failed("Oops something wrong")
    }
  }

  fun getHistory(position: Int): Purchase {
    return repository.findPurchase(position)
  }
}
