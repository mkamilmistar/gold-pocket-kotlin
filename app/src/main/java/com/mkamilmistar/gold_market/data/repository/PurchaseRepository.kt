package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.model.response.Purchase
import com.mkamilmistar.gold_market.data.model.response.PurchaseResponse

interface PurchaseRepository {
  suspend fun customerPurchases(customerId: String): List<Purchase>?
  fun findPurchaseById(purchaseId: Int): com.mkamilmistar.gold_market.data.model.entity.Purchase
  suspend fun addPurchase(customerId: String, purchase: PurchaseRequest): PurchaseResponse?
  fun deletePurchase(purchaseId: Int)
}
