package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.model.response.Customer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {
  @GET("customer/{customerId}")
  suspend fun customerById(@Path("customerId") customerId: String): Response<Customer>

}
