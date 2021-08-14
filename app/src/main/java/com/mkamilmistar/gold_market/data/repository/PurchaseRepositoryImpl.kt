package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Purchase
import com.mkamilmistar.gold_market.utils.BusinessException

class PurchaseRepositoryImpl(private val db: AppDatabase) : PurchaseRepository {
  override fun customerPurchases(customerId: Int): CustomerWithPurchases {
    val result = db.purchaseDao().getCustomerPurchases(customerId)
    if (!result.equals(null)) {
      return result
    } else {
      throw BusinessException("Gagal mendapatkan data purchases")
    }
  }

  override fun findPurchaseById(purchaseId: Int): Purchase {
    return db.purchaseDao().getPurchaseById(purchaseId)
  }

  override fun addPurchase(purchase: Purchase) {
    db.purchaseDao().insert(purchase)
  }

  override fun deletePurchase(purchaseId: Int) {
    db.purchaseDao().deleteById(purchaseId)
  }

  companion object {
    val purchaseDB: MutableList<Purchase> =
      mutableListOf(
        Purchase(1, "12 March 2021", 0, 120000, 1.0, 1),
        Purchase(2, "12 March 2021", 1, 110000, 1.0, 1),
        Purchase(3, "12 March 2021", 0, 130000, 1.0, 1),
      )
  }
}
