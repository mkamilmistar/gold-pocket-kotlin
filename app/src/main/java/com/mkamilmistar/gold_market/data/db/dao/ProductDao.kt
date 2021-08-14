package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import com.mkamilmistar.gold_market.data.model.entity.Product

@Dao
interface ProductDao: BaseDao<Product> {
}
