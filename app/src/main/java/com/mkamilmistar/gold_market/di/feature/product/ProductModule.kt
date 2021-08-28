package com.mkamilmistar.gold_market.di.feature.product

import com.mkamilmistar.gold_market.data.repository.ProductRepository
import com.mkamilmistar.gold_market.presentation.ui.home.HomeFragment
import com.mkamilmistar.gold_market.presentation.viewModel.product.ProductViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProductModule {
  @ContributesAndroidInjector
  abstract fun contributeHomeFragment(): HomeFragment

  companion object {
    @Provides
    fun provideProductViewModel(
      productRepository: ProductRepository,
    ): ProductViewModel {
      return ProductViewModel(productRepository)
    }
  }
}
