package com.mkamilmistar.gold_market.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkamilmistar.gold_market.R

class ForgetPasswordFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_forget_password, container, false)
  }

  companion object {
    @JvmStatic
    fun newInstance() = ForgetPasswordFragment()
  }
}
