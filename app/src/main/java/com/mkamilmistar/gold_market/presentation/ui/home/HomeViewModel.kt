package com.mkamilmistar.gold_market.presentation.ui.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.entity.Product
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.*
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
  private val pocketRepository: PocketRepository,
  private val productRepository: ProductRepository
) : ViewModel() {
  private var _pocketLiveData = MutableLiveData<EventResult<Pocket>>(EventResult.Idle)
  val pocketLiveData: LiveData<EventResult<Pocket>>
    get() = _pocketLiveData

  private var _productLiveData = MutableLiveData<EventResult<Product>>(EventResult.Idle)
  val productLiveData: LiveData<EventResult<Product>>
    get() = _productLiveData

  fun start(productId: Int, pocketPosition: Int) {
    updateProduct(productId)
    updatePocketActive(pocketPosition)
  }

  private fun updatePocketActive(pocketPosition: Int) {
    _pocketLiveData.value = EventResult.Loading
    try {
//      val pocketById: Pocket = pocketRepository.findPocket(pocketPosition)
      _pocketLiveData.value = EventResult.Success(Pocket(1, "", 0, 1, 1))
    } catch (e: Exception) {
      _pocketLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  private fun updateProduct(productId: Int) {
    _pocketLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          val product: Product = productRepository.getProductById(productId)
          _productLiveData.postValue(EventResult.Success(product))
        } catch (e: Exception) {
          _productLiveData.postValue(e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }

  fun createProduct(product: Product) {
    _productLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          productRepository.createProduct(product)
          _productLiveData.postValue(EventResult.Success(product))
        } catch (e: Exception) {
          _productLiveData.postValue(e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }

}
