package com.mkamilmistar.gold_market.presentation.ui.settings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.repository.ProfileRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentSettingBinding
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref
import java.util.*

class SettingFragment : Fragment() {

  private lateinit var binding: FragmentSettingBinding
  private lateinit var profileViewModel: ProfileViewModel
  private lateinit var activateCustomer: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    val sharedPreferences = SharedPref(requireContext())
    activateCustomer = sharedPreferences.retrived("ID").toString()

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
    return binding.apply {
      lifecycleOwner = this@SettingFragment
      viewmodel = profileViewModel
      fragment = this@SettingFragment
    }.root
  }

  private fun initViewModel() {
    val db = AppDatabase.getDatabase(requireContext())
    val repo = ProfileRepositoryImpl(db)
    profileViewModel =
      ViewModelProvider(this, ProfileViewModelFactory(repo)).get(ProfileViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    profileViewModel.getCustomerById(activateCustomer.toInt())
    subscribe()
    binding.apply {
      logoutSettings.setOnClickListener {
        showDialog("Are sure want to Logout?", "Click OK to Continue")
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    binding.apply {
      val profileObserver: Observer<EventResult<Customer>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("SettingFragment", "Loading Get Customer...")
          is EventResult.Success -> {
            Log.d("SettingFragment", "Success Get Customer...")
            val data = event.data
            profileName.text = "${data.firstName} ${data.lastName}"
            profileEmail.text = data.email
          }
          is EventResult.Failed -> {
            val message = "Failed to get data"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      profileViewModel.customerLivedata.observe(viewLifecycleOwner, profileObserver)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d("SettingFragment", "DESTROY")
  }

  private fun showDialog(title: String, message: String) {
    lateinit var dialog: AlertDialog
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    val dialogClickListener = DialogInterface.OnClickListener { _, which ->
      when (which) {
        DialogInterface.BUTTON_POSITIVE -> {
          findNavController().navigate(
            R.id.loginFragment, null,
            NavOptions.Builder().setPopUpTo(R.id.settingFragment, true).build()
          )
        }
        DialogInterface.BUTTON_NEUTRAL -> {
          Log.d("LOLOT", "LOLOT")
        }
      }
    }
    builder.setPositiveButton("YES", dialogClickListener)
    builder.setNeutralButton("CANCEL", dialogClickListener)
    dialog = builder.create()
    dialog.show()
  }
}
