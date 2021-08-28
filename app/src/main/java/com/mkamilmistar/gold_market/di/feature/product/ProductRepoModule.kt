package com.mkamilmistar.gold_market.di.feature.product

import com.mkamilmistar.gold_market.data.remote.api.ProductApi
import com.mkamilmistar.gold_market.data.repository.ProductRepository
import com.mkamilmistar.gold_market.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class ProductRepoModule {
  @Binds
  abstract fun bindsProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

  companion object {
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
      return retrofit.create(ProductApi::class.java)
    }
  }
}
