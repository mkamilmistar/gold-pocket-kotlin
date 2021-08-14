package com.mkamilmistar.gold_market.di

import com.mkamilmistar.gold_market.data.repository.*
import com.mkamilmistar.gold_market.presentation.ui.history.HistoryViewModel
import com.mkamilmistar.gold_market.presentation.ui.home.HomeViewModel
import com.mkamilmistar.gold_market.presentation.ui.login.LoginViewModel
import com.mkamilmistar.gold_market.presentation.ui.register.RegisterViewModel
import com.mkamilmistar.gold_market.presentation.ui.settings.SettingsViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.customer.CustomerViewModel

class DependencyContainer {
  private val customerRepository = CustomerTemp()
  private val pocketRepository = PocketTemp()
  private val productRepository = ProductRepositoryImpl()
  private val productHistoryRepository = ProductHistoryRepositoryImpl()
  private val purchaseRepository = PurchaseRepositoryImpl()

  val customerViewModel = CustomerViewModel(customerRepository)
  val homeViewModel = HomeViewModel(purchaseRepository, pocketRepository, productRepository)
  val settingsViewModel = SettingsViewModel(customerRepository)
  val loginViewModel = LoginViewModel(customerRepository)
  val registerViewModel = RegisterViewModel(customerRepository)
//  val pocketViewModel = PocketViewModelOld(pocketRepository)
  val historyViewModel = HistoryViewModel(purchaseRepository)
}
