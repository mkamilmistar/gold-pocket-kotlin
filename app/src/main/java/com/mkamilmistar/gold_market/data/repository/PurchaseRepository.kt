package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.response.Purchase

interface PurchaseRepository {
  suspend fun customerPurchases(customerId: String): List<Purchase>?
  fun findPurchaseById(purchaseId: Int): com.mkamilmistar.gold_market.data.model.entity.Purchase
  suspend fun addPurchase(customerId: String, purchase: Purchase): Purchase?
  fun deletePurchase(purchaseId: Int)
}
