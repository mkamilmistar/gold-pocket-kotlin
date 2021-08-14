package com.mkamilmistar.gold_market.presentation.viewModel.pocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.data.repository.PocketRepository

class PocketViewModelFactory(private val customerRepo: CustomerRepository, private val pocketRepo: PocketRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return PocketViewModel(customerRepo, pocketRepo) as T
  }
}
