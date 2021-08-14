package com.mkamilmistar.gold_market.presentation.viewModel.pocket

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PocketViewModel(
  private val customerRepository: CustomerRepository,
  private val pocketRepository: PocketRepository
) : ViewModel() {
  private var _pocketCustomerLiveData =
    MutableLiveData<EventResult<CustomerWithPockets>>(EventResult.Idle)
  val pocketCustomerLiveData: LiveData<EventResult<CustomerWithPockets>>
    get() = _pocketCustomerLiveData

  fun start(customerId: Int) {
    updateData(customerId)
  }

  private fun getPocketRepository(customerId: Int) = customerRepository.customerPockets(customerId)

  private fun updateData(customerId: Int) {
    _pocketCustomerLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          val pocketCustomer: CustomerWithPockets = getPocketRepository(customerId)
          _pocketCustomerLiveData.postValue(EventResult.Success(pocketCustomer))
        } catch (e: Exception) {
          _pocketCustomerLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 3000)
  }

  fun createPocket(pocket: Pocket) {
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        pocketRepository.addPocket(pocket)
      }
    }, 1000)
    updateData(1)
  }

  fun deletePocket(pocket: Pocket) {
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        pocketRepository.deletePocket(pocket)
      }
    }, 1000)
    updateData(1)
  }

  fun getPocketById(position: Int): Pocket {
      return pocketRepository.findPocketById(position)
  }

}
