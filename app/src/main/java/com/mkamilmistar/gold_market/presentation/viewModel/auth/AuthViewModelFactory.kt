package com.mkamilmistar.gold_market.presentation.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.AuthRepository

class AuthViewModelFactory(private val authRepo: AuthRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return AuthViewModel(authRepo) as T
  }
}
