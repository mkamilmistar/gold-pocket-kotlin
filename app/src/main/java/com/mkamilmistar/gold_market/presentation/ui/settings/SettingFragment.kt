package com.mkamilmistar.gold_market.presentation.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PurchaseRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentSettingBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.purchase.PurchaseViewModelFactory
import java.time.LocalDateTime
import java.util.*

class SettingFragment : Fragment() {

  private lateinit var binding: FragmentSettingBinding
  private lateinit var settingsViewModel: SettingsViewModel

  val dummyUser = Customer(0, "Melia", "Suspariana", "melia@gmail.com", "melia","melia123", "", "", "", "")

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
    return binding.apply {
      lifecycleOwner = this@SettingFragment
      viewmodel = settingsViewModel
      fragment = this@SettingFragment
    }.root
  }

  private fun initViewModel() {
    val db = AppDatabase.getDatabase(requireContext())
    val repo = CustomerRepositoryImpl(db)
    settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(repo)).get(
      SettingsViewModel::class.java)
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
