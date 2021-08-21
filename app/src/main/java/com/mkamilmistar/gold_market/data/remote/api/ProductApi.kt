package com.mkamilmistar.gold_market.data.remote.api

import com.mkamilmistar.gold_market.data.remote.entity.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
  @GET("products")
  suspend fun getProducts(): Response<List<Product>>

  @GET("product/{productId}")
  suspend fun getProductById(@Path("productId") productId: String): Response<Product>

  @POST("product")
  suspend fun createProduct(@Body request: Product) : Response<Product>
}
