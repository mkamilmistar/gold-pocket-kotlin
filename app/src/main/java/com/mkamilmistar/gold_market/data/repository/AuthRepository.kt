package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.request.LoginRequest

interface AuthRepository {
  fun login(loginRequest: LoginRequest): Customer
  fun register(customer: Customer, pocket: Pocket): Long
}

