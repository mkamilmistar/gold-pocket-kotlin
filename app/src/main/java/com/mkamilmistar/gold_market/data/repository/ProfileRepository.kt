package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.response.Customer
import com.mkamilmistar.gold_market.data.model.response.CustomerByIdResponse

interface ProfileRepository {
  suspend fun getCustomerById(customerId: String): Customer?
}
