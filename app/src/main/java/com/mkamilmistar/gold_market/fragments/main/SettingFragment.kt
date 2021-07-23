package com.mkamilmistar.gold_market.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.activities.MainActivity
import com.mkamilmistar.gold_market.helpers.Utils

class SettingFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_setting, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setFragmentResultListener(Utils.EMAIL) { key, result ->
      val dataEmail = result.getString("EMAIL")
      val emailTxt = view.findViewById<TextView>(R.id.profile_email)
      emailTxt.text = dataEmail
    }
  }
}
