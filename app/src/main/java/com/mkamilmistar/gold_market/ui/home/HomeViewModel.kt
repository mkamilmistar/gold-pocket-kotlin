package com.mkamilmistar.gold_market.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.model.Pocket
import com.mkamilmistar.gold_market.data.model.ProductHistory
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.ProductHistoryRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HomeViewModel(
  private val purchaseRepository: PurchaseRepository,
  private val pocketRepository: PocketRepository,
  private val productHistoryRepository: ProductHistoryRepository
) : ViewModel() {
  private var _pocketLiveData = MutableLiveData<EventResult<Pocket>>(EventResult.Idle)
  val pocketLiveData: LiveData<EventResult<Pocket>>
    get() = _pocketLiveData

  private var _productHistoryLiveData = MutableLiveData<EventResult<List<ProductHistory>>>(EventResult.Idle)
  val productHistoryLiveData: LiveData<EventResult<List<ProductHistory>>>
    get() = _productHistoryLiveData

  fun start(pocketPosition: Int) {
    updateProductHistory()
    updatePocketActive(pocketPosition)
  }

  private fun updatePocketActive(pocketPosition: Int) {
    _pocketLiveData.value = EventResult.Loading
    try {
      val pocketById: Pocket = pocketRepository.findPocket(pocketPosition)
      _pocketLiveData.value = EventResult.Success(pocketById)
    } catch (e: Exception) {
      _pocketLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  private fun updateProductHistory() {
    _pocketLiveData.value = EventResult.Loading
    try {
      val productHistories: List<ProductHistory> = productHistoryRepository.findAllProductHistory()
      _productHistoryLiveData.value = EventResult.Success(productHistories)
    } catch (e: Exception) {
      _productHistoryLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  fun purchaseProduct(purchase: Purchase) {
    purchaseRepository.addPurchase(purchase)
  }

}
