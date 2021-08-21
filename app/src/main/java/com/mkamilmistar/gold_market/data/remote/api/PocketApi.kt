package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.entity.Pocket
import com.mkamilmistar.gold_market.data.remote.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.remote.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.remote.response.*
import retrofit2.Response
import retrofit2.http.*

interface PocketApi {
  @GET("customer/pocket/{customerId}")
  suspend fun customerWithPockets(@Path("customerId") customerId: String): Response<List<Pocket>>

  @GET("pocket/{pocketId}")
  suspend fun pocketById(@Path("pocketId") pocketId: String): Response<Pocket>

  @POST("pocket")
  suspend fun createPocket(@Body request: CreatePocketRequest): Response<Pocket>

  @PUT("pocket")
  suspend fun updatePocket(@Body request: UpdatePocketRequest): Response<Pocket>

  @DELETE("pocket/{pocketId}")
  suspend fun deletePocketById(@Path("pocketId") pocketId: String): Response<DeletePocketResponse>
}
