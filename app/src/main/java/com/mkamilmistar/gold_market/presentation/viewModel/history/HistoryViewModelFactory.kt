package com.mkamilmistar.gold_market.presentation.viewModel.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.HistoryRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository

class HistoryViewModelFactory (private val repository: HistoryRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return HistoryViewModel(repository) as T
  }
}
