package com.mkamilmistar.gold_market.ui.register

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
import com.mkamilmistar.gold_market.databinding.FragmentRegisterBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.ui.login.LoginViewModel
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.gold_market.utils.getRandomString

class RegisterFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentRegisterBinding
  private val factory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return DependencyContainer().registerViewModel as T
    }
  }
  private val viewModel: RegisterViewModel by viewModels { factory }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentRegisterBinding.inflate(inflater, container, false)
    return binding.root
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

      btnRegister.setOnClickListener {
        val firstName = firstNameText.text.toString()
        val lastName = lastNameText.text.toString()
        val email = emailRegisterText.text.toString()
        val pwd = pwdRegisterText.text.toString()
        val newCustomer = Customer(getRandomString(5), firstName, lastName, email, pwd)
        viewModel.register(newCustomer)
      }

      termCondition.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_registerFragment_to_termAndConditionFragment)
      }
    }
  }

  private fun navigateToHome() {
    findNavController().navigate(
      R.id.homeFragment, null,
      NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
    )
  }

  private fun subscribe() {
    binding.apply {
      val customerObserver: Observer<EventResult<Customer>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> Log.d("HomeFragment", "Loading...")
          is EventResult.Success -> {
            navigateToHome()
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
}
