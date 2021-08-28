package com.mkamilmistar.gold_market.presentation.viewModel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.HistoryRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.mysimpleretrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
  private var _historyLiveData =
    MutableLiveData<Resource<List<Purchase>>>()
  val historyLiveData: LiveData<Resource<List<Purchase>>>
    get() = _historyLiveData

  fun start(customerId: String) {
    updateData(customerId)
  }

  private lateinit var purchasesCustomer: List<Purchase>

  private fun updateData(customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _historyLiveData.postValue(Resource.loading())
      val response = repository.customerPurchases(customerId)
      if (response != null) {
        _historyLiveData.postValue(Resource.success(data = response))
      } else {
        _historyLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun getPurchaseById(position: Int): Purchase {
    return purchasesCustomer[position]
  }
}
