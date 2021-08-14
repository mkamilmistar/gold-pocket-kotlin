package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases
import com.mkamilmistar.gold_market.data.model.entity.Purchase

@Dao
interface PurchaseDao: BaseDao<Purchase> {
  @Query("SELECT * FROM m_purchases where purchase_id = :purchaseId")
  fun getPurchaseById(purchaseId: Int): Purchase

  @Query("DELETE FROM m_purchases where purchase_id = :purchaseId")
  fun deleteById(purchaseId: Int)

  @Transaction
  @Query("SELECT * FROM m_customers WHERE customer_id = :id")
  fun getCustomerPurchases(id: Int): CustomerWithPurchases
}
