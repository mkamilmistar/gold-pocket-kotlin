package com.mkamilmistar.gold_market.presentation.viewModel.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository

class PurchaseViewModelFactory(private val repo: PurchaseRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return PurchaseViewModel(repo) as T
  }
}
