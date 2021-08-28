package com.mkamilmistar.gold_market.di.feature.history

import com.mkamilmistar.gold_market.data.remote.api.HistoryApi
import com.mkamilmistar.gold_market.data.remote.api.PocketApi
import com.mkamilmistar.gold_market.data.repository.HistoryRepository
import com.mkamilmistar.gold_market.data.repository.HistoryRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class HistoryRepoModule {
  @Binds
  abstract fun bindsHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository

  companion object {
    @Provides
    fun provideHistoryApi(retrofit: Retrofit): HistoryApi {
      return retrofit.create(HistoryApi::class.java)
    }
  }
}
