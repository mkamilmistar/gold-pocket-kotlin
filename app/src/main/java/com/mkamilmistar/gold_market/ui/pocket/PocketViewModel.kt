package com.mkamilmistar.gold_market.ui.pocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Pocket
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class PocketViewModel(private val repository: PocketRepository): ViewModel() {
  private var _pocketLiveData = MutableLiveData<EventResult>(EventResult.Idle)
  val pocketLiveData: LiveData<EventResult>
    get() = _pocketLiveData

  fun start() {
    updateData()
  }

  private fun getPocketRepository() = repository.findAllPocket()

  private fun updateData() {
    _pocketLiveData.value = EventResult.Loading
    try {
      val history: List<Pocket> = getPocketRepository()
      _pocketLiveData.value = EventResult.Success(history)
    } catch (e: Exception) {
      _pocketLiveData.value = EventResult.Failed("Oops something wrong")
    }
  }

  fun getPocket(position: Int): Pocket {
    return repository.findPocket(position)
  }

  fun deletePocket(position: Int) {
    repository.deletePocket(position)
    updateData()
  }

  fun addPocket(pocket: Pocket) {
    repository.addPocket(pocket)
    updateData()
  }

}
