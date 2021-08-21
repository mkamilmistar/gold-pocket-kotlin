package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.remote.entity.Product

interface ProductRepository {
  suspend fun getProductById(productId: String): Product?
  suspend fun getProducts(): List<Product>?
  suspend fun createProduct(request: Product): Product?
}
