package com.mkamilmistar.gold_market.presentation.viewModel.customer

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.request.LoginRequest
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CustomerViewModel(private val customerRepository: CustomerRepository) : ViewModel() {
  private var _customerLiveData = MutableLiveData<EventResult<Customer>>()
  val customerLivedata: LiveData<EventResult<Customer>>
    get() {
      return _customerLiveData
    }

  fun customerLogin(loginRequest: LoginRequest) {
    _customerLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          val customerData = customerRepository.customerLogin(loginRequest)
          _customerLiveData.postValue(EventResult.Success(data = customerData))

        } catch (e: Exception) {
          _customerLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 2000)
  }

  fun registerCustomer(customer: Customer) {
    _customerLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      try {
        viewModelScope.launch(Dispatchers.IO) {
          customerRepository.register(customer)
          _customerLiveData.postValue(EventResult.Success(customer))
        }
      } catch (e: Exception) {
        _customerLiveData.postValue(e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
      }
    }, 2000)
  }
}
