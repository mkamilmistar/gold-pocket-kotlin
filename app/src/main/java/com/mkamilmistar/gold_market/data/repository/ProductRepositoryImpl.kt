package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Product

class ProductRepositoryImpl(
  private val db: AppDatabase
) : ProductRepository {
  override fun getProductById(productId: Int): Product {
    return db.productDao().getProduct(productId)
  }

  override fun createProduct(product: Product): Product {
    db.productDao().insert(product)
    return product
  }
}
