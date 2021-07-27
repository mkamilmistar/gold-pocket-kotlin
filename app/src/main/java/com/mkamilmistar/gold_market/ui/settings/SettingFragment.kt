package com.mkamilmistar.gold_market.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentSettingBinding
import com.mkamilmistar.gold_market.helpers.Utils

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
        Navigation.findNavController(view)
          .navigate(R.id.action_settingFragment_to_loginFragment)
      }
    }
  }
}