package com.mkamilmistar.gold_market.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HomeViewModel(private val repository: CustomerRepository) : ViewModel() {
  private lateinit var email: String
  private lateinit var pwd: String
  private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)
  val customerLiveData: LiveData<EventResult>
    get() = _customerLiveData

  fun constructor(email: String, pwd: String) {
    this.email = email
    this.pwd = pwd
  }

  fun start() {
    updateData()
  }

  private fun getCustomerFromRepository(email: String, pwd: String): Customer {
    return repository.getCustomer(email, pwd)
  }

  private fun updateData() {
    _customerLiveData.value = EventResult.Loading
    try {
      val customer: Customer = getCustomerFromRepository(email, pwd)
      _customerLiveData.value = EventResult.Success(customer)
    } catch (e: Exception) {
      _customerLiveData.value = EventResult.Failed("Oops something wrong")
    }
  }

}
