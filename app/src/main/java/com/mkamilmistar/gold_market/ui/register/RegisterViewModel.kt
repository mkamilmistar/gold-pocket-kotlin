package com.mkamilmistar.gold_market.ui.register

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class RegisterViewModel(private val repository: CustomerRepository): ViewModel() {
  private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)
  val customerLiveData: LiveData<EventResult>
    get() = _customerLiveData

  private fun registerToRepository(customer: Customer) {
    return repository.register(customer)
  }

  fun register(customer: Customer) {
    _customerLiveData.value = EventResult.Loading
    Handler(Looper.getMainLooper()).postDelayed({
      try {
        registerToRepository(customer)
        _customerLiveData.value = EventResult.Success(customer)
      } catch (e: Exception) {
        _customerLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
      }
    }, 2000)
  }
}
