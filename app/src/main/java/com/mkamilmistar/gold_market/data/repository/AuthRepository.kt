package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.remote.request.LoginRequest
import com.mkamilmistar.gold_market.data.remote.request.RegisterRequest
import com.mkamilmistar.gold_market.data.remote.response.LoginResponse
import com.mkamilmistar.gold_market.data.remote.response.RegisterResponse

interface AuthRepository {
  suspend fun login(loginRequest: LoginRequest): LoginResponse?
  suspend fun register(request: RegisterRequest): RegisterResponse?
}

