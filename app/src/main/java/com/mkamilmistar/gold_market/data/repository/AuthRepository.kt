package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.request.LoginRequest
import com.mkamilmistar.gold_market.data.model.request.RegisterRequest
import com.mkamilmistar.gold_market.data.model.response.LoginResponse
import com.mkamilmistar.gold_market.data.model.response.RegisterResponse

interface AuthRepository {
  suspend fun login(loginRequest: LoginRequest): LoginResponse?
  suspend fun register(request: RegisterRequest): RegisterResponse?
}

