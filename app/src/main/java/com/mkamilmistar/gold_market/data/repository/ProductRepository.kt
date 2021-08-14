package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Product

interface ProductRepository {
//  fun findAllProduct(): List<Product>
  fun getProductById(productId: Int): Product
  fun createProduct(product: Product): Product
}
