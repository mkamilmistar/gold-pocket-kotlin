package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.mkamilmistar.gold_market.data.model.entity.Purchase

@Dao
interface PurchaseDao: BaseDao<Purchase> {
//  @Query("SELECT * FROM m_purchases")
}
