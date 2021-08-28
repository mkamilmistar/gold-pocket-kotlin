package com.mkamilmistar.gold_market.presentation.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.remote.request.RegisterRequest
import com.mkamilmistar.gold_market.databinding.FragmentRegisterBinding
import com.mkamilmistar.gold_market.presentation.viewModel.auth.AuthViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.ViewModelFactoryBase
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class RegisterFragment : DaggerFragment(), TextWatcher {

  private lateinit var binding: FragmentRegisterBinding
  private lateinit var authViewModel: AuthViewModel

  @Inject
  lateinit var pocketViewModels: PocketViewModel

  @Inject
  lateinit var sharedPreferences: SharedPref

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
    return binding.apply {
      lifecycleOwner = this@RegisterFragment
      fragment = this@RegisterFragment
      viewmodel = authViewModel
    }.root
  }

  private fun initViewModel() {
    ViewModelProvider(this, ViewModelFactoryBase {
      authViewModel }).get(AuthViewModel::class.java)
    ViewModelProvider(this, ViewModelFactoryBase {
      pocketViewModels }).get(PocketViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    binding.apply {
      btnRegister.isEnabled = false
      firstNameText.addTextChangedListener(this@RegisterFragment)
      lastNameText.addTextChangedListener(this@RegisterFragment)
      emailRegisterText.addTextChangedListener(this@RegisterFragment)
      pwdRegisterText.addTextChangedListener(this@RegisterFragment)

      termCondition.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_registerFragment_to_termAndConditionFragment)
      }

      textSignIn.setOnClickListener {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
      }
    }
  }

  fun register() {
    binding.apply {
      val firstName = firstNameText.text.toString()
      val lastName = lastNameText.text.toString()
      val email = emailRegisterText.text.toString()
      val pwd = pwdRegisterText.text.toString()
      val newCustomer =
        RegisterRequest(
          firstName = firstName, lastName = lastName,
          email = email, username = "", password = pwd, address = "", birthDate = "", status = 1
        )
      authViewModel.registerCustomer(newCustomer)
    }
  }

  private fun navigateToHome() {
    findNavController().navigate(
      R.id.homeFragment, null,
      NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
    )
  }

  private fun subscribe() {
    hideProgressBar()
    binding.apply {
      authViewModel.successRegister.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("AuthApi", "Register Success : ${it.data}")
            val customer = it.data
            if (customer!=null) {
              sharedPreferences.save(Utils.CUSTOMER_ID, customer.id)
              pocketViewModels.start(customer.id)
            }
            subscribePocket()
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

  private fun subscribePocket() {
    hideProgressBar()
    binding.apply {
      pocketViewModels.pocketCustomerLiveData.observe(viewLifecycleOwner, {
        when (it.status) {
          ResourceStatus.LOADING -> showProgressBar()
          ResourceStatus.SUCCESS -> {
            Log.d("PocketApi", "Subscribe : ${it.data}")
            val customerPockets = it.data
            when {
              customerPockets?.isNotEmpty() == true -> {
                sharedPreferences.save(Utils.POCKET_ID, it.data.first().id)
              }
              else -> {
                sharedPreferences.save(Utils.POCKET_ID, "")
              }
            }
            navigateToHome()
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

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    binding.apply {
      btnRegister.isEnabled =
        (firstNameText.text.toString().isNotEmpty() &&
          lastNameText.text.toString().isNotEmpty() &&
          emailRegisterText.text.toString().isNotEmpty() &&
          emailRegisterText.text.toString().contains("@") &&
          pwdRegisterText.text.toString().isNotEmpty() &&
          pwdRegisterText.text.toString().length >= 6
          )
    }
  }

  override fun afterTextChanged(s: Editable?) {
  }

  private fun hideProgressBar() {
    binding.progressBarRegister.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarRegister.visibility = View.VISIBLE
  }
}
