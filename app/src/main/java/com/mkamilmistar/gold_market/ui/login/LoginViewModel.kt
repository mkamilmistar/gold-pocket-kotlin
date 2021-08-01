package com.mkamilmistar.gold_market.ui.login

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class LoginViewModel(private val repository: CustomerRepository) : ViewModel() {
  private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)
  val customerLiveData: LiveData<EventResult>
    get() = _customerLiveData

  private fun getCustomerFromRepository(email: String, pwd: String): Customer {
    return repository.getCustomer(email, pwd)
  }

  fun login(email: String, pwd: String) {
    _customerLiveData.value = EventResult.Loading
    Handler(Looper.getMainLooper()).postDelayed({
      try {
        val customer: Customer = getCustomerFromRepository(email, pwd)
        _customerLiveData.value = EventResult.Success(customer)
      } catch (e: Exception) {
        _customerLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
      }
    }, 2000)
  }
}
