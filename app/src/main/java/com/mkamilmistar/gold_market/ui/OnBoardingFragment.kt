package com.mkamilmistar.gold_market.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

  private lateinit var binding: FragmentOnBoardingBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    @Suppress("DEPRECATION")
    requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.apply {
      btnGetStarted.setOnClickListener {
        Navigation.findNavController(view)
          .navigate(R.id.action_onBoardingFragment_to_loginFragment)
      }
    }
  }

  companion object {
    @JvmStatic
    fun newInstance() = OnBoardingFragment()
  }
}
