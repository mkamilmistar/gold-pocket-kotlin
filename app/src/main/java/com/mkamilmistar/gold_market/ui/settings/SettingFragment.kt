package com.mkamilmistar.gold_market.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentSettingBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.ui.register.RegisterViewModel

class SettingFragment : Fragment() {

  private lateinit var binding: FragmentSettingBinding
  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().settingsViewModel as T
    }
  }
  private val settingsViewModel: SettingsViewModel by viewModels { factory }
  val dummyUser = CustomerRepositoryImpl().customerDBImport

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
    return binding.apply {
      lifecycleOwner = this@SettingFragment
      viewmodel = settingsViewModel
      fragment = this@SettingFragment
    }.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.apply {
      logoutSettings.setOnClickListener {
        findNavController().navigate(
          R.id.loginFragment, null,
          NavOptions.Builder().setPopUpTo(R.id.settingFragment, true).build()
        )
      }
    }
  }
}
