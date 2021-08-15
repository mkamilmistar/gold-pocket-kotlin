package com.mkamilmistar.gold_market.presentation.viewModel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkamilmistar.gold_market.data.repository.ProfileRepository

class ProfileViewModelFactory (private val profileRepo: ProfileRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return ProfileViewModel(profileRepo) as T
  }

}
