package com.mkamilmistar.gold_market.di.feature.history

import com.mkamilmistar.gold_market.data.repository.HistoryRepository
import com.mkamilmistar.gold_market.presentation.ui.history.HistoryFragment
import com.mkamilmistar.gold_market.presentation.viewModel.history.HistoryViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class HistoryModule {
  @ContributesAndroidInjector
  abstract fun contributeHistoryFragment(): HistoryFragment

  companion object {
    @Provides
    fun provideAuthViewModel(
      historyRepository: HistoryRepository,
    ): HistoryViewModel {
      return HistoryViewModel(historyRepository)
    }
  }
}
