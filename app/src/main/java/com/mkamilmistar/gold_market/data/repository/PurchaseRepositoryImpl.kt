package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Purchase

class PurchaseRepositoryImpl: PurchaseRepository {
  override fun findAllPurchase(): List<Purchase> {
    return purchaseDB
  }

  override fun findPurchase(position: Int): Purchase {
    return purchaseDB[position]
  }

  override fun addPurchase(pocket: Purchase) {
    purchaseDB.add(pocket)
  }

  override fun deletePurchase(position: Int) {
    purchaseDB.removeAt(position)
  }

  companion object {
    val purchaseDB: MutableList<Purchase> =
      mutableListOf(
        Purchase("Purchase-1", "12 March 2021", 1, 12000, 0.0),
        Purchase("Purchase-2", "12 March 2021", 1, 11000, 0.0),
        Purchase("Purchase-3", "12 March 2021", 1, 13000, 0.0),
        )
  }
}
