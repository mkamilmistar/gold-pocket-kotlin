package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.request.LoginRequest
import com.mkamilmistar.gold_market.data.model.request.RegisterRequest
import com.mkamilmistar.gold_market.data.model.response.BaseResponse
import com.mkamilmistar.gold_market.data.model.response.LoginResponse
import com.mkamilmistar.gold_market.data.model.response.RegisterResponse
import com.mkamilmistar.gold_market.data.remote.api.AuthApi
import com.mkamilmistar.gold_market.utils.BusinessException

class AuthRepositoryImpl(
  private val authApi: AuthApi
) : AuthRepository {


  override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
    return try {
      val response = authApi.login(loginRequest)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("AuthApi", e.localizedMessage)
      null
    }
  }

  override suspend fun register(request: RegisterRequest): RegisterResponse? {
    return try {
      val response = authApi.register(request)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("AuthApi", e.localizedMessage)
      null
    }
  }

}
