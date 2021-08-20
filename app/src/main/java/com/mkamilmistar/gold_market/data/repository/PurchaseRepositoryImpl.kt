package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.model.response.Purchase
import com.mkamilmistar.gold_market.data.remote.api.PurchaseApi

class PurchaseRepositoryImpl(private val db: AppDatabase, private val purchaseApi: PurchaseApi) : PurchaseRepository {
  override suspend fun customerPurchases(customerId: String): List<Purchase>? {
    return try {
      val response = purchaseApi.customerPurchasesList(customerId)
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

  override fun findPurchaseById(purchaseId: Int): com.mkamilmistar.gold_market.data.model.entity.Purchase {
    return db.purchaseDao().getPurchaseById(purchaseId)
  }

  override suspend fun addPurchase(customerId: String, request: PurchaseRequest): Purchase? {
    return try {
      val response = purchaseApi.purchase(customerId, request)
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

  override fun deletePurchase(purchaseId: Int) {
    db.purchaseDao().deleteById(purchaseId)
  }
}
