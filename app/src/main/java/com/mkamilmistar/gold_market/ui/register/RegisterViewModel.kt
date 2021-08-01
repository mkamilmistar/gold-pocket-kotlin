package com.mkamilmistar.gold_market.ui.register

import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepository

class RegisterViewModel(private val repository: CustomerRepository): ViewModel() {

  fun registerCustomer(customer: Customer) {
    repository.register(customer)
  }

}
