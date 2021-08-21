package com.mkamilmistar.gold_market.data.remote

import com.mkamilmistar.gold_market.BuildConfig
import com.mkamilmistar.gold_market.data.remote.api.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
  }

  val authApi by lazy {
    retrofit.create(AuthApi::class.java)
  }

  val customerApi by lazy {
    retrofit.create(CustomerApi::class.java)
  }

  val pocketApi by lazy {
    retrofit.create(PocketApi::class.java)
  }

  val purchaseApi by lazy {
    retrofit.create(PurchaseApi::class.java)
  }

  val profileApi by lazy {
    retrofit.create(ProfileApi::class.java)
  }

  val productApi by lazy {
    retrofit.create(ProductApi::class.java)
  }
}
