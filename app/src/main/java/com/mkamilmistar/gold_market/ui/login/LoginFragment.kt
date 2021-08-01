package com.mkamilmistar.gold_market.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentLoginBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentLoginBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.apply {
      btnSignIn.isEnabled = false
      loginEmail.addTextChangedListener(this@LoginFragment)
      loginPassword.addTextChangedListener(this@LoginFragment)

      btnSignIn.setOnClickListener {
        findNavController().navigate(
          R.id.homeFragment, null,
          NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        )
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

  companion object {
    @JvmStatic
    fun newInstance() = LoginFragment()
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
