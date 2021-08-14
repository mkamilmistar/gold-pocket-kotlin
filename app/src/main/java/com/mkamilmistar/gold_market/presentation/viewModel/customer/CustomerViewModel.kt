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

  private var _successRegister = MutableLiveData<EventResult<Long>>()
  val successRegister: LiveData<EventResult<Long>>
    get() {
      return _successRegister
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
    }, 1000)
  }

  fun registerCustomer(customer: Customer) {
    _successRegister.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          val id = customerRepository.register(customer)
          _successRegister.postValue(EventResult.Success(data = id))
        } catch (e: Exception) {
          _successRegister.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }

  fun getCustomerById(customerId: Int) {
    _customerLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          val customerData = customerRepository.getCustomerById(customerId)
          _customerLiveData.postValue(EventResult.Success(data = customerData))
        } catch (e: Exception) {
          _customerLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }
}