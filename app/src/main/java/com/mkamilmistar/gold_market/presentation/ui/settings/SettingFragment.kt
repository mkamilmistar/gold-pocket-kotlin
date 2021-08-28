package com.mkamilmistar.gold_market.presentation.ui.settings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.remote.RetrofitInstance
import com.mkamilmistar.gold_market.data.repository.ProfileRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentSettingBinding
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.profile.ProfileViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.ViewModelFactoryBase
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class SettingFragment : DaggerFragment() {

  private lateinit var binding: FragmentSettingBinding
  private lateinit var activateCustomer: String

  @Inject
  lateinit var profileViewModel: ProfileViewModel

  @Inject
  lateinit var sharedPreferences: SharedPref

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    activateCustomer = sharedPreferences.retrieved(Utils.CUSTOMER_ID).toString()

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
    return binding.apply {
      lifecycleOwner = this@SettingFragment
      viewmodel = profileViewModel
      fragment = this@SettingFragment
    }.root
  }

  private fun initViewModel() {
    ViewModelProvider(this, ViewModelFactoryBase {
      profileViewModel }).get(ProfileViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    profileViewModel.getCustomerById(activateCustomer)
    subscribe()
    binding.apply {
      logoutSettings.setOnClickListener {
        showDialog("Are sure want to Logout?", "Click OK to Continue")
      }
    }
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    hideProgressBar()
    binding.apply {
      profileViewModel.customerLivedata.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("profileApi", "Subscribe : ${it.data}")
            val data = it.data
            if (data != null) {
              profileName.text = "${data.firstName} ${data.lastName}"
              profileEmail.text = data.email
            }
            hideProgressBar()
          }
          ResourceStatus.ERROR -> {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            hideProgressBar()
          }
        }
      })
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
          sharedPreferences.clear()
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

  private fun hideProgressBar() {
    binding.progressBarSettings.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarSettings.visibility = View.VISIBLE
  }
}
