package com.mkamilmistar.gold_market.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.CustomerRepository

class SettingsViewModelFactory(private val repository: CustomerRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return SettingsViewModel(repository) as T
  }
}
