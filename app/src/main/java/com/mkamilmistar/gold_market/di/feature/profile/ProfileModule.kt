package com.mkamilmistar.gold_market.di.feature.profile

import com.mkamilmistar.gold_market.data.repository.ProfileRepository
import com.mkamilmistar.gold_market.presentation.ui.home.HomeFragment
import com.mkamilmistar.gold_market.presentation.ui.settings.SettingFragment
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileModule {
  @ContributesAndroidInjector
  abstract fun contributeHomeFragment(): HomeFragment

  @ContributesAndroidInjector
  abstract fun contributeSettingsFragment(): SettingFragment

  companion object {
    @Provides
    fun provideProfileViewModel(
      profileRepository: ProfileRepository,
    ): ProfileViewModel {
      return ProfileViewModel(profileRepository)
    }
  }
}
