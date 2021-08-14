package com.mkamilmistar.gold_market.presentation.viewModel.history

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: PurchaseRepository) : ViewModel() {
  private var _historyLiveData =
    MutableLiveData<EventResult<CustomerWithPurchases>>(EventResult.Idle)
  val historyLiveData: LiveData<EventResult<CustomerWithPurchases>>
    get() = _historyLiveData

  fun start(customerId: Int) {
    updateData(customerId)
  }

  private lateinit var purchasesCustomer: CustomerWithPurchases


  private fun updateData(customerId: Int) {
    _historyLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          purchasesCustomer = repository.customerPurchases(customerId)
          _historyLiveData.postValue(EventResult.Success(purchasesCustomer))
        } catch (e: Exception) {
          _historyLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 2000)
  }

  fun getPurchaseById(position: Int): Purchase {
    return purchasesCustomer.purchases[position]
  }
}
