package com.mkamilmistar.gold_market.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.fragments.bottomNavigation.HistoryFragment
import com.mkamilmistar.gold_market.fragments.bottomNavigation.HomeFragment
import com.mkamilmistar.gold_market.fragments.bottomNavigation.ProfileFragment

class MainActivity : AppCompatActivity() {

  private val homeFragment = HomeFragment()
  private val historyFragment = HistoryFragment()
  private val profileFragment = ProfileFragment()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    replaceFragment(homeFragment)

    val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
    bottomNav.setOnItemSelectedListener{
      when(it.itemId){
        R.id.ic_home -> {
          replaceFragment(homeFragment)
        }
        R.id.ic_history -> replaceFragment(historyFragment)
        R.id.ic_profile -> replaceFragment(profileFragment)
      }
      true
    }
  }

  private fun replaceFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.fragment_container, fragment)
    transaction.commit()
  }
}
