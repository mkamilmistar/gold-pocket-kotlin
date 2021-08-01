package com.mkamilmistar.gold_market.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.ProductHistory
import com.mkamilmistar.gold_market.data.repository.ProductHistoryRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HistoryViewModel(private val repository: ProductHistoryRepository): ViewModel() {
  private var _historyLiveData = MutableLiveData<EventResult>(EventResult.Idle)
  val historyLiveData: LiveData<EventResult>
    get() = _historyLiveData

  fun start() {
    updateData()
  }

  private fun getHistoryRepository() = repository.findAllProductHistory()

  private fun updateData() {
    _historyLiveData.value = EventResult.Loading
    try {
      val history: List<ProductHistory> = getHistoryRepository()
      _historyLiveData.value = EventResult.Success(history)
    } catch (e: Exception) {
      _historyLiveData.value = EventResult.Failed("Oops something wrong")
    }
  }

  fun getHistory(position: Int): ProductHistory {
    return repository.findProductHistory(position)
  }
}
