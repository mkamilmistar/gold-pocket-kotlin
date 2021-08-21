package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.remote.entity.Product
import com.mkamilmistar.gold_market.data.remote.api.ProductApi

class ProductRepositoryImpl(
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

  override suspend fun createProduct(request: Product): Product? {
    return try {
      val response = productApi.createProduct(request)
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
}
