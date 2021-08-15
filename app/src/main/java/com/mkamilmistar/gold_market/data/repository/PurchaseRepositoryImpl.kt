package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Pocket
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
}
