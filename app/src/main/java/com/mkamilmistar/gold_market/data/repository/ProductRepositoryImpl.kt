package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Product

class ProductRepositoryImpl: ProductRepository {
  override fun findAllProduct(): List<Product> {
    return productDB
  }

  override fun findProduct(position: Int): Product {
    return productDB[position]
  }

  companion object {
    val productDB: MutableList<Product> =
      mutableListOf(
       Product("Product-1", "Gold", 100000,
         120000, "", 1, "", ""),
        Product("Product-2", "Platinum", 120000,
          130000, "", 1, "", ""),
        Product("Product-3", "Silver", 70000,
          80000, "", 1, "", ""),
      )
  }
}
