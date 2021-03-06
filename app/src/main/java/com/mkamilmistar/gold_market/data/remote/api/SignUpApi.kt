package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.request.RegisterRequest
import com.mkamilmistar.gold_market.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
  @POST("auth/register")
  suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}
