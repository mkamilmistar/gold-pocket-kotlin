package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Purchase

interface PurchaseRepository {
  fun customerPurchases(customerId: Int): CustomerWithPurchases
  fun findPurchaseById(purchaseId: Int): Purchase
  fun addPurchase(purchase: Purchase)
  fun deletePurchase(purchaseId: Int)
}
