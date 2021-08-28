package com.mkamilmistar.gold_market.di.feature.purchase

import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.presentation.ui.home.HomeFragment
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class PurchaseModule {
  @ContributesAndroidInjector
  abstract fun contributeHomeFragment(): HomeFragment

  companion object {
    @Provides
    fun providePurchaseViewModel(
      purchaseRepository: PurchaseRepository,
    ): PurchaseViewModel {
      return PurchaseViewModel(purchaseRepository)
    }
  }
}
