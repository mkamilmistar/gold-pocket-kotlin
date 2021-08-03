package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Purchase

interface PurchaseRepository {
  fun findAllPurchase(): List<Purchase>
  fun findPurchase(position: Int): Purchase
  fun addPurchase(purchase: Purchase)
  fun deletePurchase(position: Int)
}
