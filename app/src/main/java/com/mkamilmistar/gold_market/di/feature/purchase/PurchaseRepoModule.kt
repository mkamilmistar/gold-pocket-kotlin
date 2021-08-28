package com.mkamilmistar.gold_market.di.feature.purchase

import com.mkamilmistar.gold_market.data.remote.api.PurchaseApi
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class PurchaseRepoModule {
  @Binds
  abstract fun bindsPurchaseRepository(purchaseRepositoryImpl: PurchaseRepositoryImpl): PurchaseRepository

  companion object {
    @Provides
    fun providePurchaseApi(retrofit: Retrofit): PurchaseApi {
      return retrofit.create(PurchaseApi::class.java)
    }
  }
}
