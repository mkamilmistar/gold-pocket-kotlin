package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.LoginRequest

interface CustomerRepository {
  fun customerLogin(loginRequest: LoginRequest): Customer
  fun register(customer: Customer)
  fun customerPockets(customerId: Int): CustomerWithPockets
}

