package com.mkamilmistar.gold_market.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HomeViewModel(
  private val customerRepository: CustomerRepository,
  private val purchaseRepository: PurchaseRepository
) : ViewModel() {
  private var _customerLiveData = MutableLiveData<EventResult<Customer>>(EventResult.Idle)
  val customerLiveData: LiveData<EventResult<Customer>>
    get() = _customerLiveData

  fun start(email: String, pwd: String) {
    updateDataCustomer(email, pwd)
  }

  private fun getCustomerFromRepository(email: String, pwd: String): Customer {
    return customerRepository.getCustomer(email, pwd)
  }

  private fun updateDataCustomer(email: String, pwd: String) {
    _customerLiveData.value = EventResult.Loading
      try {
        val customer: Customer = getCustomerFromRepository(email, pwd)
        _customerLiveData.value = EventResult.Success(customer)
      } catch (e: Exception) {
        _customerLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
      }
  }

  fun purchaseProduct(purchase: Purchase) {
    purchaseRepository.addPurchase(purchase)
  }

}
