package com.mkamilmistar.gold_market.ui.pocket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentPocketBinding
import com.mkamilmistar.gold_market.databinding.FragmentRegisterBinding

class PocketFragment : Fragment() {

  private lateinit var binding: FragmentPocketBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentPocketBinding.inflate(inflater, container, false)
    return binding.root
  }

  companion object {
    @JvmStatic
    fun newInstance() = PocketFragment()
  }
}
