package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.ProductHistory

class ProductHistoryRepositoryImpl : ProductHistoryRepository {
  override fun findAllProductHistory(): List<ProductHistory> {
    return productHistoryDB
  }

  override fun findProductHistory(position: Int): ProductHistory {
    return productHistoryDB[position]
  }

  override fun addProductHistory(productHistory: ProductHistory) {
    productHistoryDB.add(productHistory)
  }

  override fun deleteProductHistory(position: Int) {
    productHistoryDB.removeAt(position)
  }

  companion object {
    val productHistoryDB: MutableList<ProductHistory> =
      mutableListOf(
        ProductHistory("History-1", "12 March 2021", 100000, 120000),
        ProductHistory("History-2", "14 March 2021", 120000, 130000),
        ProductHistory("History-3", "30 March 2021", 110000, 130000)
      )
  }

  val productDBImport
    get() = productHistoryDB.last()
}
