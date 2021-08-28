package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.remote.request.LoginRequest
import com.mkamilmistar.gold_market.data.remote.request.RegisterRequest
import com.mkamilmistar.gold_market.data.remote.response.LoginResponse
import com.mkamilmistar.gold_market.data.remote.response.RegisterResponse
import com.mkamilmistar.gold_market.data.remote.api.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
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
