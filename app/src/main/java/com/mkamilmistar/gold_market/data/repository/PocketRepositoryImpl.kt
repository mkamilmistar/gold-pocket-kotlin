package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.remote.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.remote.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.remote.response.*
import com.mkamilmistar.gold_market.data.remote.api.PocketApi
import com.mkamilmistar.gold_market.data.remote.entity.Pocket
import javax.inject.Inject

class PocketRepositoryImpl @Inject constructor(private val pocketApi: PocketApi): PocketRepository {

  override suspend fun customerPockets(customerId: String): List<Pocket>? {
    return try {
      val response = pocketApi.customerWithPockets(customerId)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("PocketApi", e.localizedMessage)
      null
    }
  }

  override suspend fun updatePocket(request: UpdatePocketRequest): Pocket? {
    return try {
      val response = pocketApi.updatePocket(request)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("PocketApi", e.localizedMessage)
      null
    }
  }

  override suspend fun findPocketById(pocketId: String): Pocket? {
    return try {
      val response = pocketApi.pocketById(pocketId)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("PocketApi", e.localizedMessage)
      null
    }
  }

  override suspend fun addPocket(request: CreatePocketRequest): Pocket? {
    return try {
      val response = pocketApi.createPocket(request)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("PocketApi", e.localizedMessage)
      null
    }
  }

  override suspend fun deletePocket(pocketId: String): DeletePocketResponse? {
    return try {
      val response = pocketApi.deletePocketById(pocketId)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("PocketApi", e.localizedMessage)
      null
    }
  }
}
