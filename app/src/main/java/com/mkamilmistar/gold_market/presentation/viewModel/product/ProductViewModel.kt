package com.mkamilmistar.gold_market.presentation.viewModel.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.remote.entity.Product
import com.mkamilmistar.gold_market.data.repository.ProductRepository
import com.mkamilmistar.gold_market.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

  fun createProduct() {
    val newProduct = Product(
      productName = "Gold",
      productImage = "gold.jpg",
      productPriceBuy = 100000,
      productPriceSell = 120000,
      productStatus = 1,
      historyPrices = listOf()
    )

    viewModelScope.launch(Dispatchers.IO) {
      _productListLiveData.postValue(Resource.loading())
      val response = productRepository.createProduct(newProduct)
      if (response != null) {
        _productListLiveData.postValue(Resource.success(data = listOf(response)))
      } else {
        _productListLiveData.postValue(Resource.error(message = response))
      }
    }
  }
}
