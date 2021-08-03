package com.mkamilmistar.gold_market.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

  private lateinit var binding: FragmentSettingBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("SettingsFragment", "onCreate")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d("SettingsFragment", "onDestroy")
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSettingBinding.inflate(inflater, container, false)
    return binding.root
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
