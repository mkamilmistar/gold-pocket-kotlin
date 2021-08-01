package com.mkamilmistar.gold_market.di

import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.ProductHistoryRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.ProductRepositoryImpl
import com.mkamilmistar.gold_market.ui.home.HomeViewModel
import com.mkamilmistar.gold_market.ui.login.LoginViewModel
import com.mkamilmistar.gold_market.ui.pocket.PocketViewModel
import com.mkamilmistar.gold_market.ui.register.RegisterViewModel
import com.mkamilmistar.gold_market.ui.settings.SettingsViewModel

class DependencyContainer {
  private val customerRepository = CustomerRepositoryImpl()
  private val pocketRepository = PocketRepositoryImpl()
  private val productRepository = ProductRepositoryImpl()
  private val productHistoryRepository = ProductHistoryRepositoryImpl()

  val homeViewModel = HomeViewModel(customerRepository)
  val settingsViewModel = SettingsViewModel(customerRepository)
  val loginViewModel = LoginViewModel(customerRepository)
  val registerViewModel = RegisterViewModel(customerRepository)
  val pocketViewModel = PocketViewModel(pocketRepository)
}
