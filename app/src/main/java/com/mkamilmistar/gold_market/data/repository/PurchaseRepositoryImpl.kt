package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.remote.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import com.mkamilmistar.gold_market.data.remote.response.PurchaseResponse
import com.mkamilmistar.gold_market.data.remote.api.PurchaseApi
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(private val purchaseApi: PurchaseApi) : PurchaseRepository {

  override suspend fun addPurchase(customerId: String, purchase: PurchaseRequest): PurchaseResponse? {
    return try {
      val response = purchaseApi.purchase(purchase, customerId)
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
