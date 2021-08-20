package com.mkamilmistar.gold_market.presentation.viewModel.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.model.response.Purchase
import com.mkamilmistar.gold_market.data.model.response.PurchaseResponse
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.mysimpleretrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PurchaseViewModel (private val purchaseRepository: PurchaseRepository): ViewModel() {
  private var _purchaseLiveData = MutableLiveData<Resource<PurchaseResponse>>()
  val purchaseLiveData: LiveData<Resource<PurchaseResponse>>
    get() = _purchaseLiveData

  fun purchaseProduct(customerId: String, purchase: PurchaseRequest) {
    viewModelScope.launch(Dispatchers.IO) {
      _purchaseLiveData.postValue(Resource.loading())
      val response = purchaseRepository.addPurchase(customerId, purchase)
      if (response != null) {
        _purchaseLiveData.postValue(Resource.success(data = response))
      } else {
        _purchaseLiveData.postValue(Resource.error(message = response))
      }
    }
  }
}
