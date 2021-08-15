package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.LoginRequest
import com.mkamilmistar.gold_market.utils.BusinessException

class AuthRepositoryImpl(
  private val db: AppDatabase
) : AuthRepository {


  override fun login(loginRequest: LoginRequest): Customer {
    val data = db.authDao().getDataLogin(loginRequest.email, loginRequest.password)
    if (!data.equals(null)) {
      return data
    } else {
      throw BusinessException("Email or Password incorrect")
    }
  }

  override fun register(customer: Customer): Long {
    return db.authDao().insert(customer)
  }
}
