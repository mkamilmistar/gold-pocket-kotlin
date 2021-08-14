package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.LoginRequest

class CustomerTemp: CustomerRepository {
  override fun customerLogin(loginRequest: LoginRequest): Customer {
    TODO("Not yet implemented")
  }

  override fun register(customer: Customer): Long {
    TODO("Not yet implemented")
  }

  override fun customerPockets(customerId: Int): CustomerWithPockets {
    TODO("Not yet implemented")
  }
}
