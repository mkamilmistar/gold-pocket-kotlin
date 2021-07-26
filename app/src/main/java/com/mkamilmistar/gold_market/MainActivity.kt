package com.mkamilmistar.gold_market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mkamilmistar.gold_market.fragments.main.HistoryFragment
import com.mkamilmistar.gold_market.fragments.main.HomeFragment
import com.mkamilmistar.gold_market.fragments.main.SettingFragment
import com.mkamilmistar.gold_market.helpers.Utils

class MainActivity : AppCompatActivity() {

  private val homeFragment = HomeFragment()
  private val historyFragment = HistoryFragment()
  private val profileFragment = SettingFragment()
  lateinit var emailPass: String
  lateinit var pwdPass: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    replaceFragment(homeFragment)

    emailPass = intent.getStringExtra(Utils.EMAIL).toString()
    pwdPass = intent.getStringExtra(Utils.PASSWORD).toString()

    val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
    bottomNav.setOnItemSelectedListener{
      when(it.itemId){
        R.id.ic_home -> replaceFragment(homeFragment)
        R.id.ic_history -> replaceFragment(historyFragment)
        R.id.ic_profile -> replaceFragment(profileFragment)
      }
      true
    }
  }

  private fun replaceFragment(fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    when(fragment) {
      is SettingFragment -> {
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragment.setFragmentResult(Utils.EMAIL, bundleOf("EMAIL" to emailPass))
        fragmentTransaction.commit()
      }
      else -> {
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
      }
    }

  }
}
