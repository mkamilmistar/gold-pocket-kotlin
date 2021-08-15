package com.mkamilmistar.gold_market.presentation.viewModel.product

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.Product
import com.mkamilmistar.gold_market.data.repository.ProductRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductViewModel(private val productRepository: ProductRepository): ViewModel() {
  private var _productLiveData = MutableLiveData<EventResult<Product>>(EventResult.Idle)
  val productLiveData: LiveData<EventResult<Product>>
    get() = _productLiveData

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

  private fun updateProduct(productId: Int) {
    _productLiveData.postValue(EventResult.Loading)
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
}
