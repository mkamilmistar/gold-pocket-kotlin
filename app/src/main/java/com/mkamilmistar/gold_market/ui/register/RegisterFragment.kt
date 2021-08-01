package com.mkamilmistar.gold_market.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentRegisterBinding
import com.mkamilmistar.gold_market.utils.Utils

class RegisterFragment : Fragment(), TextWatcher {

  private lateinit var binding: FragmentRegisterBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentRegisterBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.apply {
      btnRegister.isEnabled = false
      firstNameText.addTextChangedListener(this@RegisterFragment)
      lastNameText.addTextChangedListener(this@RegisterFragment)
      emailRegisterText.addTextChangedListener(this@RegisterFragment)
      pwdRegisterText.addTextChangedListener(this@RegisterFragment)

      val email = emailRegisterText.text
      val bundle = bundleOf(Utils.EMAIL to email)
      btnRegister.setOnClickListener {
        Navigation.findNavController(view).navigate(
          R.id.homeFragment, bundle,
          NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
        )
      }

      termCondition.setOnClickListener {
        Navigation.findNavController(view)
//          .navigate(R.id.action_registerFragment_to_termAndConditionFragment)
      }
    }
  }

  companion object {
    @JvmStatic
    fun newInstance() = RegisterFragment()
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
