package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.utils.BusinessException

class CustomerRepositoryImpl : CustomerRepository {
  override fun getCustomer(email: String, password: String): Customer {
    if(email != customerDB.email || password != customerDB.password) {
      throw BusinessException("Email or Password incorrect")
    }
    return customerDB
  }

  override fun register(customer: Customer) {
    customerDB = customer
  }

  companion object {
    var customerDB = Customer("", "Melia", "Suspariana", "@", "123123")
  }
}
