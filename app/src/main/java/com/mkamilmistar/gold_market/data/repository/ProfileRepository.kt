package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.remote.entity.Customer

interface ProfileRepository {
  suspend fun getCustomerById(customerId: String): Customer?
}
