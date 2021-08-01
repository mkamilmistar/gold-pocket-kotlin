package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Product

interface ProductRepository {
  fun findAllProduct(): List<Product>
  fun findProduct(position: Int): Product
}
