package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.LoginRequest

interface CustomerRepository {
  fun customerLogin(loginRequest: LoginRequest): Customer
  fun getCustomerById(customerId: Int): Customer
  fun register(customer: Customer): Long
  fun customerPockets(customerId: Int): CustomerWithPockets
}

