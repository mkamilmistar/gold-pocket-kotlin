package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import com.mkamilmistar.gold_market.data.remote.response.PurchaseResponse
import retrofit2.Response
import retrofit2.http.*

interface PurchaseApi {
  @POST("purchase")
  suspend fun purchase(
    @Body request:PurchaseRequest,
    @Query("customerId") customerId: String
  ): Response<PurchaseResponse>

  @GET("customer/purchases/{customerId}")
  suspend fun customerPurchasesList(@Path("customerId") customerId: String): Response<List<Purchase>>
}
