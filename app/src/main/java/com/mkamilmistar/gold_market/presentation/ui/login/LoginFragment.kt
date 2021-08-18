package com.mkamilmistar.gold_market.presentation.ui.login

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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.LoginRequest
import com.mkamilmistar.gold_market.data.model.response.LoginResponse
import com.mkamilmistar.gold_market.data.remote.RetrofitInstance
import com.mkamilmistar.gold_market.data.repository.AuthRepositoryImpl
import com.mkamilmistar.gold_market.data.repository.PocketRepositoryImpl
import com.mkamilmistar.gold_market.databinding.FragmentLoginBinding
import com.mkamilmistar.gold_market.helpers.EventResult
import com.mkamilmistar.gold_market.presentation.viewModel.auth.AuthViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.auth.AuthViewModelFactory
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModel
import com.mkamilmistar.gold_market.presentation.viewModel.pocket.PocketViewModelFactory
import com.mkamilmistar.gold_market.utils.SharedPref
import com.mkamilmistar.gold_market.utils.Utils
import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus

class LoginFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentLoginBinding
  private lateinit var authViewModel: AuthViewModel
  private lateinit var pocketViewModels: PocketViewModel

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
    val authRetrofit = RetrofitInstance.authApi
    val authRepo = AuthRepositoryImpl(authRetrofit)

    val db = AppDatabase.getDatabase(requireContext())
    val pocketRepo = PocketRepositoryImpl(db)
    authViewModel = ViewModelProvider(this, AuthViewModelFactory(authRepo)).get(AuthViewModel::class.java)
    pocketViewModels = ViewModelProvider(this, PocketViewModelFactory(pocketRepo)).get(
      PocketViewModel::class.java)
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
          hideProgressBar()
        }
        ResourceStatus.ERROR -> {
          Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
          hideProgressBar()
        }
      }
    })
  }

  private fun subscribePocket() {
    hideProgressBar()
    binding.apply {
      val pocketListObserver: Observer<EventResult<CustomerWithPockets>> = Observer { event ->
        when (event) {
          is EventResult.Loading -> showProgressBar()
          is EventResult.Success -> {
            val customerPockets = event.data
            val sharedPreferences = SharedPref(requireContext())
            sharedPreferences.save(Utils.POCKET_ID,customerPockets.pockets.first().pocketId.toString())
            navToHome()
            hideProgressBar()
          }
          is EventResult.Failed -> {
            hideProgressBar()
            Toast.makeText(context, event.errorMessage.toString(), Toast.LENGTH_SHORT).show()
          }
          else -> {
          }
        }
      }
      pocketViewModels.pocketCustomerLiveData.observe(viewLifecycleOwner, pocketListObserver)
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
