package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.request.CustomerByIdRequest
import com.mkamilmistar.gold_market.data.model.response.Customer
import com.mkamilmistar.gold_market.data.model.response.CustomerByIdResponse
import com.mkamilmistar.gold_market.data.remote.api.ProfileApi
import com.mkamilmistar.gold_market.utils.BusinessException

class ProfileRepositoryImpl(private val db: AppDatabase, private val profileApi: ProfileApi): ProfileRepository {

  override suspend fun getCustomerById(customerId: String): Customer? {
    return try {
      val response = profileApi.customerById(customerId)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("ProfileApi", e.localizedMessage)
      null
    }
  }
}
