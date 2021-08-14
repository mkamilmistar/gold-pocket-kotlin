package com.mkamilmistar.gold_market.presentation.viewModel.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.CustomerRepository

class CustomerViewModelFactory (private val repository: CustomerRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return CustomerViewModel(repository) as T
  }

}
