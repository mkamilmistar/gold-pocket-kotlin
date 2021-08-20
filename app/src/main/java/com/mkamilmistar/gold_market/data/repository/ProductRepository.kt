package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.response.Product

interface ProductRepository {
//  fun findAllProduct(): List<Product>
  suspend fun getProductById(productId: String): Product?
  suspend fun getProducts(): List<Product>?
//  fun createProduct(product: Product): Product
}
