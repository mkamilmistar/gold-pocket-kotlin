package com.mkamilmistar.gold_market.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mkamilmistar.gold_market.MainActivity
import com.mkamilmistar.gold_market.R

class SplashScreenFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_splash_screen, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Handler(Looper.getMainLooper()).postDelayed({
      findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
    }, 3000)
  }

  companion object {
    @JvmStatic
    fun newInstance() = SplashScreenFragment()
  }
}
