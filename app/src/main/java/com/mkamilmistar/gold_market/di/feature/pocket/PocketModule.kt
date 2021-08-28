package com.mkamilmistar.gold_market.di.feature.pocket

import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.presentation.ui.home.HomeFragment
import com.mkamilmistar.gold_market.presentation.ui.login.LoginFragment
import com.mkamilmistar.gold_market.presentation.ui.pocket.PocketFragment
import com.mkamilmistar.gold_market.presentation.ui.register.RegisterFragment
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class PocketModule {
  @ContributesAndroidInjector
  abstract fun contributePocketFragment(): PocketFragment

  companion object {
    @Provides
    fun providePocketViewModel(
      pocketRepository: PocketRepository,
    ): PocketViewModel {
      return PocketViewModel(pocketRepository)
    }
  }
}
