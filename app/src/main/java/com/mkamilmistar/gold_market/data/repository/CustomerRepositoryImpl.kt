package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Customer

class CustomerRepositoryImpl : CustomerRepository {
  override fun getCustomer(email: String, password: String): Customer {
    return customerDB
  }

  override fun register(customer: Customer) {
    customerDB = customer
  }

  companion object {
    var customerDB = Customer("", "Melia", "Suspariana", "melia@gmail.com", "melia123")
  }
}
