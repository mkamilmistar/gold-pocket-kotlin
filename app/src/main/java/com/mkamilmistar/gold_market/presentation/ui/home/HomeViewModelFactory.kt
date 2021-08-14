package com.mkamilmistar.gold_market.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.ProductRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository

class HomeViewModelFactory (
  private val purchaseRepository: PurchaseRepository,
  private val pocketRepository: PocketRepository,
  private val productRepository: ProductRepositoryImpl
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return HomeViewModel(purchaseRepository, pocketRepository, productRepository) as T
  }
}
