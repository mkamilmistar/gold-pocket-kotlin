package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Customer

interface ProfileRepository {
  fun getCustomerById(customerId: Int): Customer
}
