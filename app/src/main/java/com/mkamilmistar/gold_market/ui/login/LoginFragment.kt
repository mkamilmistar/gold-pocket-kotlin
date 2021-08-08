package com.mkamilmistar.gold_market.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.databinding.FragmentLoginBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.ui.pocket.PocketViewModel

class LoginFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentLoginBinding
  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().loginViewModel as T
    }
  }
  private val loginViewModel: LoginViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
    return binding.apply {
      lifecycleOwner = this@LoginFragment
      fragment = this@LoginFragment
      viewmodel = loginViewModel
    }.root
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

  private fun navToHome(customer: Customer) {
    val bundle = bundleOf("CUSTOMER" to customer)
    findNavController().navigate(
      R.id.homeFragment, bundle,
      NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
    )
  }

  fun textSignUp() {
    findNavController()
      .navigate(R.id.action_loginFragment_to_registerFragment)
  }

  fun login() {
    binding.apply {
      val email = loginEmail.text.toString()
      val password = loginPassword.text.toString()
      loginViewModel.login(email, password)
    }
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    hideProgressBar()
    binding.apply {
      val customerObserver: Observer<EventResult<Customer>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            val customer: Customer = event.data
            navToHome(customer)
            hideProgressBar()
          }
          is EventResult.Failed -> {
            val message = event.errorMessage.toString()
            hideProgressBar()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      loginViewModel.customerLiveData.observe(viewLifecycleOwner, customerObserver)
    }
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
