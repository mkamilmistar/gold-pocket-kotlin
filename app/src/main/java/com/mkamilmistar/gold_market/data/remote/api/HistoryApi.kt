package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryApi {
  @GET("customer/purchases/{customerId}")
  suspend fun customerPurchasesList(@Path("customerId") customerId: String): Response<List<Purchase>>
}
