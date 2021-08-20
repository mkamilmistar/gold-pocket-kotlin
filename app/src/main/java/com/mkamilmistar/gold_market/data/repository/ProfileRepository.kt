package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.response.Customer

interface ProfileRepository {
  suspend fun getCustomerById(customerId: String): Customer?
}
