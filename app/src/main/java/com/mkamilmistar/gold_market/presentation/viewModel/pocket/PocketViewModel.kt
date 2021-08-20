package com.mkamilmistar.gold_market.presentation.viewModel.pocket

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.model.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.model.response.Pocket
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.mysimpleretrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PocketViewModel(
  private val pocketRepository: PocketRepository
) : ViewModel() {
  private var _pocketCustomerLiveData =
    MutableLiveData<Resource<List<Pocket>>>()
  val pocketCustomerLiveData: LiveData<Resource<List<Pocket>>>
    get() = _pocketCustomerLiveData

  private var _pocketLiveData = MutableLiveData<Resource<Pocket>>()
  val pocketLiveData: LiveData<Resource<Pocket>>
    get() = _pocketLiveData

  private lateinit var customerPockets: List<Pocket>
  lateinit var pocketCustomer: Pocket

  fun start(customerId: String) {
    pocketCustomerList(customerId)
  }

  private fun pocketCustomerList(customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _pocketCustomerLiveData.postValue(Resource.loading())
      val response = pocketRepository.customerPockets(customerId)
      if (response != null) {
        customerPockets = response
        _pocketCustomerLiveData.postValue(Resource.success(data = response))
      } else {
        _pocketCustomerLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun getPocketWithCustomerIdAndPocketId(pocketId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _pocketLiveData.postValue(Resource.loading())
      val response = pocketRepository.findPocketById(pocketId)
      if (response != null) {
        _pocketLiveData.postValue(Resource.success(data = response))
      } else {
        _pocketLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun updatePocket(request: UpdatePocketRequest, customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      pocketRepository.updatePocket(request)
    }
    pocketCustomerList(customerId)
  }


  fun createPocket(request: CreatePocketRequest, customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      pocketRepository.addPocket(request)
    }
    pocketCustomerList(customerId)
  }

  fun deletePocket(pocketId: String, customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      pocketRepository.deletePocket(pocketId)
    }
    pocketCustomerList(customerId)
  }

  fun getPocketById(position: Int): Pocket {
    return customerPockets[position]
  }

//  private fun updatePocketActive(pocketPosition: Int) {
//    _pocketLiveData.value = EventResult.Loading
//    try {
////      val pocketById: Pocket = pocketRepository.findPocket(pocketPosition)
//      _pocketLiveData.value = EventResult.Success(Pocket(1, "", 0, 1, 1))
//    } catch (e: Exception) {
//      _pocketLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
//    }
//  }

}
