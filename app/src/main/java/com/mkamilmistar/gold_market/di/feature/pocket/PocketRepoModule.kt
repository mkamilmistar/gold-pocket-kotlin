package com.mkamilmistar.gold_market.di.feature.pocket

import com.mkamilmistar.gold_market.data.remote.api.PocketApi
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class PocketRepoModule {
  @Binds
  abstract fun bindsPocketRepository(pocketRepositoryImpl: PocketRepositoryImpl): PocketRepository

  companion object {
    @Provides
    fun providePocketApi(retrofit: Retrofit): PocketApi {
      return retrofit.create(PocketApi::class.java)
    }
  }
}
