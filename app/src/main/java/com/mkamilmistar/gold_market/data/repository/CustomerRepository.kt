package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Customer

interface CustomerRepository {
  fun getCustomer(email: String, password: String): Customer
}

