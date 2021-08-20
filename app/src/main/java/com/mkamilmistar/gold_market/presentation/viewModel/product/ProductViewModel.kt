package com.mkamilmistar.gold_market.presentation.viewModel.product

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.response.Product
import com.mkamilmistar.gold_market.data.model.response.ProductResponse
import com.mkamilmistar.gold_market.data.repository.ProductRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.mysimpleretrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductViewModel(private val productRepository: ProductRepository): ViewModel() {
  private var _productListLiveData = MutableLiveData<Resource<List<Product>>>()
  val productListLiveData: LiveData<Resource<List<Product>>>
    get() = _productListLiveData

  private var _productLiveData = MutableLiveData<Resource<Product>>()
  val productLiveData: LiveData<Resource<Product>>
    get() = _productLiveData

  fun getProduct(productId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _productLiveData.postValue(Resource.loading())
      val response = productRepository.getProductById(productId)
      if (response != null) {
        _productLiveData.postValue(Resource.success(data = response))
      } else {
        _productLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun getProductList() {
    viewModelScope.launch(Dispatchers.IO) {
      _productListLiveData.postValue(Resource.loading())
      val response = productRepository.getProducts()
      if (response != null) {
        _productListLiveData.postValue(Resource.success(data = response))
      } else {
        _productListLiveData.postValue(Resource.error(message = response))
      }
    }
  }
}
