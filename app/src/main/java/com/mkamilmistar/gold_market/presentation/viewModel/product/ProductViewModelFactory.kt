package com.mkamilmistar.gold_market.presentation.viewModel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.ProductRepository

class ProductViewModelFactory(
  private val productRepository: ProductRepository
): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ProductViewModel(productRepository) as T
  }
}
