package com.mkamilmistar.gold_market

import android.app.Application
import com.mkamilmistar.gold_market.di.DependencyContainer

class BaseApplication: Application() {
  private val container: DependencyContainer by lazy {
    DependencyContainer()
  }

  fun getLoginViewModel() = container.loginViewModel
  fun getRegisterViewModel() = container.registerViewModel
  fun getHomeViewModel() = container.homeViewModel
  fun getPocketViewModel() = container.pocketViewModel
  fun getSettingsViewModel() = container.settingsViewModel
  fun getHistoryViewModel() = container
}
