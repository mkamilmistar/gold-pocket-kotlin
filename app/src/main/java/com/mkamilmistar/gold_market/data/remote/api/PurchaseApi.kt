package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.model.response.Purchase
import retrofit2.Response
import retrofit2.http.*

interface PurchaseApi {
  @POST("purchase")
  suspend fun purchase(
    @Query("customerId") customerId: String,
    @Body request: Purchase
  ): Response<Purchase>

  @GET("customer/purchases/{customerId}")
  suspend fun customerPurchasesList(@Path("customerId") customerId: String): Response<List<Purchase>>
}
