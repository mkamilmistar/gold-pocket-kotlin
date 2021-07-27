package com.mkamilmistar.gold_market.ui.history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mkamilmistar.gold_market.MainActivity
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.databinding.FragmentHistoryBinding
import com.mkamilmistar.gold_market.databinding.FragmentLoginBinding

class HistoryFragment : Fragment() {

  private lateinit var binding: FragmentHistoryBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("HistoryFragment", "onCreate")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d("HistoryFragment", "onDestroy")
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentHistoryBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

}
