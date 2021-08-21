package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.request.CustomerByIdRequest
import com.mkamilmistar.gold_market.data.remote.entity.Customer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerApi {
  @GET("customer/{customerId}")
  suspend fun customerWithPockets(@Path("customerId") request: CustomerByIdRequest): Response<Customer>

}
