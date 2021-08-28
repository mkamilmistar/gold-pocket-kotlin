package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.remote.api.HistoryApi
import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val historyApi: HistoryApi): HistoryRepository {
  override suspend fun customerPurchases(customerId: String): List<Purchase>? {
    return try {
      val response = historyApi.customerPurchasesList(customerId)
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
