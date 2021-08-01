package com.mkamilmistar.gold_market.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
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
import com.mkamilmistar.gold_market.ui.home.HomeViewModel
import com.mkamilmistar.gold_market.utils.Utils

class LoginFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentLoginBinding
  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().loginViewModel as T
    }
  }
  private val viewModel: LoginViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    subscribe()
    binding.apply {
      btnSignIn.isEnabled = false
      loginEmail.addTextChangedListener(this@LoginFragment)
      loginPassword.addTextChangedListener(this@LoginFragment)

      btnSignIn.setOnClickListener {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()
        viewModel.login(email, password)
      }

      btnForget.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
      }

      textSignUp.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_loginFragment_to_registerFragment)
      }
    }
  }

  private fun navToHome(email: String, pwd: String) {
    val bundle = bundleOf("EMAIL" to email, "PASSWORD" to pwd)
    findNavController().navigate(
      R.id.homeFragment, bundle,
      NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
    )
  }

  @SuppressLint("SetTextI18n")
  private fun subscribe() {
    binding.apply {
      val customerObserver: Observer<EventResult> = Observer<EventResult> { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HomeFragment", "Loading...")
          is EventResult.Success -> {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            navToHome(email, password)
            Log.d("LoginFragment", "Success...")
          }
          is EventResult.Failed -> {
            val message = event.errorMessage.toString()
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      viewModel.customerLiveData.observe(viewLifecycleOwner, customerObserver)
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
}
