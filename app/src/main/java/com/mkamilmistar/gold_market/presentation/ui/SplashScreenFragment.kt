package com.mkamilmistar.gold_market.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.MainActivity
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

  private lateinit var binding: FragmentSplashScreenBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    @Suppress("DEPRECATION")
    requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    (activity as MainActivity).supportActionBar?.hide()
    binding.apply {

      val topAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.top_animation)
      val botAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_animation)

      splashLogo.startAnimation(topAnim)
      logoText.startAnimation(botAnim)
      sloganText.startAnimation(botAnim)
    }

    Handler(Looper.getMainLooper()).postDelayed({
      findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
    }, 3000)
  }

  companion object {
    @JvmStatic
    fun newInstance() = SplashScreenFragment()
  }
}
