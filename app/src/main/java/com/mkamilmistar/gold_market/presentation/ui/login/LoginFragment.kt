package com.mkamilmistar.gold_market.presentation.ui.login

import android.annotation.SuppressLint
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
import com.mkamilmistar.gold_market.data.remote.request.LoginRequest
import com.mkamilmistar.gold_market.databinding.FragmentLoginBinding
import com.mkamilmistar.gold_market.presentation.viewModel.auth.AuthViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.ViewModelFactoryBase
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment(), TextWatcher {

  private lateinit var binding: FragmentLoginBinding
  private lateinit var authViewModel: AuthViewModel

  @Inject
  lateinit var pocketViewModels: PocketViewModel

  private val sharedPreferences: SharedPref = SharedPref(requireContext())

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
    return binding.apply {
      lifecycleOwner = this@LoginFragment
      fragment = this@LoginFragment
      viewmodel = authViewModel
      pocketVM = pocketViewModels
    }.root
  }

  private fun initViewModel() {
    ViewModelProvider(this, ViewModelFactoryBase {
      authViewModel }).get(AuthViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    binding.apply {
      btnSignIn.isEnabled = false
      loginEmail.addTextChangedListener(this@LoginFragment)
      loginPassword.addTextChangedListener(this@LoginFragment)

      btnForget.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
      }
    }
  }

  private fun navToHome() {
    findNavController().navigate(
      R.id.homeFragment, null,
      NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
    )
  }

  fun textSignUp() {
    findNavController()
      .navigate(R.id.action_loginFragment_to_registerFragment)
  }

  fun login() {
    binding.apply {
      val login = LoginRequest(
        loginEmail.text.toString(),
        loginPassword.text.toString()
      )
      authViewModel.customerLogin(login)
    }
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    hideProgressBar()
    authViewModel.customerLivedata.observe(viewLifecycleOwner, {
      when (it.status) {
        ResourceStatus.LOADING -> showProgressBar()
        ResourceStatus.SUCCESS -> {
          Log.d("AuthApi", "Subscribe : ${it.data}")
          val customer = it.data
          if (customer!=null) {
            sharedPreferences.save(Utils.CUSTOMER_ID, customer.userId)
            pocketViewModels.start(customer.userId)
          }
          subscribePocket()
          hideProgressBar()
        }
        ResourceStatus.ERROR -> {
          Toast.makeText(requireContext(), "Failed Login", Toast.LENGTH_SHORT).show()
          hideProgressBar()
        }
      }
    })
  }

  private fun subscribePocket() {
    hideProgressBar()
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
          navToHome()
          hideProgressBar()
        }
        ResourceStatus.ERROR -> {
          Toast.makeText(requireContext(), "Failed Get Data Pocket", Toast.LENGTH_SHORT).show()
          hideProgressBar()
        }
      }
    })
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    binding.apply {
      btnSignIn.isEnabled =
        (loginEmail.text.toString().isNotEmpty() &&
          loginEmail.text.toString().contains("@") &&
          loginPassword.text.toString().isNotEmpty() &&
          loginPassword.text.toString().length >= 6
          )
    }
  }

  override fun afterTextChanged(s: Editable?) {
  }

  private fun hideProgressBar() {
    binding.progressBarLogin.visibility = View.GONE
  }

  private fun showProgressBar() {
    binding.progressBarLogin.visibility = View.VISIBLE
  }
}
