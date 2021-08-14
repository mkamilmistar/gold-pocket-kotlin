package com.mkamilmistar.gold_market.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.entity.Product
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.*
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HomeViewModel(
  private val purchaseRepository: PurchaseRepository,
  private val pocketRepository: PocketRepository,
  private val productRepository: ProductRepositoryImpl
) : ViewModel() {
  private var _pocketLiveData = MutableLiveData<EventResult<Pocket>>(EventResult.Idle)
  val pocketLiveData: LiveData<EventResult<Pocket>>
    get() = _pocketLiveData

  private var _productLiveData = MutableLiveData<EventResult<Product>>(EventResult.Idle)
  val productLiveData: LiveData<EventResult<Product>>
    get() = _productLiveData

  fun start(productPosition: Int, pocketPosition: Int) {
    updateProductHistory(productPosition)
    updatePocketActive(pocketPosition)
  }

  private fun updatePocketActive(pocketPosition: Int) {
    _pocketLiveData.value = EventResult.Loading
    try {
//      val pocketById: Pocket = pocketRepository.findPocket(pocketPosition)
      _pocketLiveData.value = EventResult.Success(Pocket(1,"",0,1))
    } catch (e: Exception) {
      _pocketLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  private fun updateProductHistory(productPosition: Int) {
    _pocketLiveData.value = EventResult.Loading
    try {
      val productHistories: Product = productRepository.findProduct(productPosition)
      _productLiveData.value = EventResult.Success(productHistories)
    } catch (e: Exception) {
      _productLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  fun purchaseProduct(purchase: Purchase) {
    purchaseRepository.addPurchase(purchase)
  }

}
