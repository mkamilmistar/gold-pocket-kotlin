package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.ProductHistory


interface ProductHistoryRepository {
  fun findAllProductHistory(): List<ProductHistory>
  fun findProductHistory(position: Int): ProductHistory
  fun addProductHistory(productHistory: ProductHistory)
  fun deleteProductHistory(position: Int)
}
