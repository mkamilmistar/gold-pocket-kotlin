package com.mkamilmistar.gold_market.di.feature.auth

import com.mkamilmistar.gold_market.data.repository.AuthRepository
import com.mkamilmistar.gold_market.presentation.ui.login.LoginFragment
import com.mkamilmistar.gold_market.presentation.ui.register.RegisterFragment
import com.mkamilmistar.gold_market.presentation.viewModel.auth.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthModule {
  @ContributesAndroidInjector
  abstract fun contributeLoginFragment(): LoginFragment

  @ContributesAndroidInjector
  abstract fun contributeRegisterFragment(): RegisterFragment

  companion object {
    @Provides
    fun provideAuthViewModel(
      authRepository: AuthRepository,
    ): AuthViewModel {
      return AuthViewModel(authRepository)
    }
  }
}
