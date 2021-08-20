package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.response.Product
import com.mkamilmistar.gold_market.data.remote.api.ProductApi

class ProductRepositoryImpl(
  private val db: AppDatabase,
  private val productApi: ProductApi
) : ProductRepository {

  override suspend fun getProductById(productId: String): Product? {
    return try {
      val response = productApi.getProductById(productId)
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("ProductApi", e.localizedMessage)
      null
    }
  }

  override suspend fun getProducts(): List<Product>? {
    return try {
      val response = productApi.getProducts()
      if (response.isSuccessful) {
        response.body()
      } else {
        null
      }
    } catch (e: Exception) {
      Log.e("ProductApi", e.localizedMessage)
      null
    }
  }

//  override fun createProduct(product: Product): Product {
//    db.productDao().insert(product)
//    return product
//  }
}
