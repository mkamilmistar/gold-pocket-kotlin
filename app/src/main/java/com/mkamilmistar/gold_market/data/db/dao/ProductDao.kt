package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.mkamilmistar.gold_market.data.db.entity.Product

@Dao
interface ProductDao : BaseDao<Product> {
  @Query("SELECT * FROM m_products WHERE product_id=:productId")
  fun getProduct(productId: Int): Product
}
