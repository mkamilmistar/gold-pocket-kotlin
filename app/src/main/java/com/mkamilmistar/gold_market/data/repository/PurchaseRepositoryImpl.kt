package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Purchase

class PurchaseRepositoryImpl: PurchaseRepository {
  override fun findAllPurchase(): List<Purchase> {
    return purchaseDB
  }

  override fun findPurchase(position: Int): Purchase {
    return purchaseDB[position]
  }

  override fun addPurchase(purchase: Purchase) {
    purchaseDB.add(purchase)
  }

  override fun deletePurchase(position: Int) {
    purchaseDB.removeAt(position)
  }

  companion object {
    val purchaseDB: MutableList<Purchase> =
      mutableListOf(
        Purchase("Purchase-1", "12 March 2021", 1, 120000, 1.0),
        Purchase("Purchase-2", "12 March 2021", 1, 110000, 1.0),
        Purchase("Purchase-3", "12 March 2021", 1, 130000, 1.0),
        )
  }
}
