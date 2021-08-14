package com.mkamilmistar.gold_market.presentation.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.repository.CustomerRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentRegisterBinding
import com.mkamilmistar.gold_market.di.DependencyContainer
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.customer.CustomerViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.customer.CustomerViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref
import java.time.LocalDateTime
import java.util.*

class RegisterFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentRegisterBinding
  private lateinit var customerViewModel: CustomerViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    initViewModel()
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
    return binding.apply {
      lifecycleOwner = this@RegisterFragment
      fragment = this@RegisterFragment
      viewmodel = customerViewModel
    }.root
  }

  private fun initViewModel() {
    val db = AppDatabase.getDatabase(requireContext())
    val repo = CustomerRepositoryImpl(db)
    customerViewModel = ViewModelProvider(this, CustomerViewModelFactory(repo)).get(CustomerViewModel::class.java)
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
        Customer(
          firstName = firstName, lastName = lastName,
          email = email, username = "", password = pwd, address = "", birthDate = "", status = "", token = "")
      customerViewModel.registerCustomer(newCustomer)
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
    val sharedPreferences = SharedPref(requireContext())
    binding.apply {
      val customerObserver: Observer<EventResult<Long>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            val idCustomer = event.data
            Log.d("TOLOL", idCustomer.toString())
            sharedPreferences.save("ID", idCustomer.toString())
            navigateToHome()
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
      customerViewModel.successRegister.observe(viewLifecycleOwner, customerObserver)
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
