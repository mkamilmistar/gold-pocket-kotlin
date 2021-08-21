package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.request.LoginRequest
import com.mkamilmistar.gold_market.data.remote.request.RegisterRequest
import com.mkamilmistar.gold_market.data.remote.response.LoginResponse
import com.mkamilmistar.gold_market.data.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
  @POST("auth/login")
  suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

  @POST("auth/register")
  suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}
