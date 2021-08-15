package com.mkamilmistar.gold_market.presentation.viewModel.pocket

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PocketViewModel(
  private val pocketRepository: PocketRepository
) : ViewModel() {
  private var _pocketCustomerLiveData =
    MutableLiveData<EventResult<CustomerWithPockets>>(EventResult.Idle)
  val pocketCustomerLiveData: LiveData<EventResult<CustomerWithPockets>>
    get() = _pocketCustomerLiveData

  private var _pocketLiveData = MutableLiveData<EventResult<Pocket>>(EventResult.Idle)
  val pocketLiveData: LiveData<EventResult<Pocket>>
    get() = _pocketLiveData
  private lateinit var customerPockets: CustomerWithPockets
  private lateinit var pocketCustomer: Pocket


  fun start(customerId: Int) {
    pocketCustomerList(customerId)
  }

  private fun pocketCustomerList(customerId: Int) {
    _pocketCustomerLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          customerPockets = pocketRepository.customerPockets(customerId)
          _pocketCustomerLiveData.postValue(EventResult.Success(customerPockets))
        } catch (e: Exception) {
          _pocketCustomerLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }

  fun getPocketWithCustomerIdAndPocketId(customerId: Int, pocketId: Int) {
    _pocketLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          pocketCustomer = pocketRepository.getPocketByCustomerAndPocketId(customerId, pocketId)
          _pocketLiveData.postValue(EventResult.Success(pocketCustomer))
        } catch (e: Exception) {
          _pocketLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }

  fun updatePocket(pocket: Pocket, customerId: Int) {
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        pocketRepository.updatePocket(pocket)
      }
    }, 1000)
    pocketCustomerList(customerId)
  }


  fun createPocket(pocket: Pocket, customerId: Int) {
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        pocketRepository.addPocket(pocket)
      }
    }, 1000)
    pocketCustomerList(customerId)
  }

  fun deletePocket(position: Int, customerId: Int) {
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        val delPocket = customerPockets.pockets[position]
        pocketRepository.deletePocket(delPocket.pocketId)
      }
    }, 1000)
    pocketCustomerList(customerId)
  }

  fun getPocketById(position: Int): Pocket {
    return customerPockets.pockets[position]
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

}
