package com.mkamilmistar.gold_market.presentation.viewModel.purchase

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PurchaseViewModel (private val purchaseRepository: PurchaseRepository): ViewModel() {
  private var _isSuccess = MutableLiveData<EventResult<Boolean>>(EventResult.Idle)
  val isSuccess: LiveData<EventResult<Boolean>>
    get() = _isSuccess

  fun purchaseProduct(purchase: Purchase) {
    _isSuccess.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          purchaseRepository.addPurchase(purchase)
          _isSuccess.postValue(EventResult.Success(true))
        } catch (e: Exception) {
          _isSuccess.postValue(e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }
}
