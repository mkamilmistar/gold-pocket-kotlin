package com.mkamilmistar.gold_market.fragments.bottomNavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.helpers.Utils

class ProfileFragment : Fragment() {

  lateinit var txtView: TextView

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_profile, container, false)
    txtView = view.findViewById(R.id.text_profile_fragment)
    val dataEmail = arguments?.getString(Utils.EMAIL)
    val dataPwd = arguments?.getString(Utils.PASSWORD)
    txtView.text = dataEmail.toString()

    return view
  }
}
