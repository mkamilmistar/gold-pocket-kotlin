package com.mkamilmistar.gold_market.presentation.viewModel.pocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.remote.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.remote.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.remote.entity.Pocket
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.utils.Resource
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

  private var _isExistDataValidate =
    MutableLiveData<Resource<Boolean>>()
  val isExistDataValidate: LiveData<Resource<Boolean>>
    get() = _isExistDataValidate

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

  fun getPocketCustomerById(pocketId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _pocketLiveData.postValue(Resource.loading())
      val response = pocketRepository.findPocketById(pocketId)
      if (response != null) {
        pocketCustomer = response
        _pocketLiveData.postValue(Resource.success(data = response))
      } else {
        _pocketLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun updatePocket(input: String, position: Int, customerId: String, productId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _pocketLiveData.postValue(Resource.loading())
      val activePocket = customerPockets[position]
      val request = UpdatePocketRequest(
        pocketName = input,
        product = CreatePocketRequest.ProductId(productId),
        customer = CreatePocketRequest.CustomerId(customerId),
        id = activePocket.id,
        pocketQty = activePocket.pocketQty
      )
      val response = pocketRepository.updatePocket(request)
      if (response != null) {
        pocketCustomer = response
        _pocketLiveData.postValue(Resource.success(data = response))
        pocketCustomerList(customerId)
      } else {
        _pocketLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun createPocket(request: CreatePocketRequest, customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      pocketRepository.addPocket(request)
      pocketCustomerList(customerId)
    }
  }

  fun deletePocket(position: Int, customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _isExistDataValidate.postValue(Resource.loading())
      val delPocket = customerPockets[position]
      if (delPocket.pocketQty > 0.0) {
        _isExistDataValidate.postValue(Resource.success(false))
      } else {
        _isExistDataValidate.postValue(Resource.success(data = true))
        pocketRepository.deletePocket(delPocket.id)
        pocketCustomerList(customerId)
      }
    }
  }

  fun getPocketById(position: Int): Pocket {
    return customerPockets[position]
  }

}
