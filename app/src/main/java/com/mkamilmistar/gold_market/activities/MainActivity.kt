package com.mkamilmistar.gold_market.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.fragments.bottomNavigation.HistoryFragment
import com.mkamilmistar.gold_market.fragments.bottomNavigation.HomeFragment
import com.mkamilmistar.gold_market.fragments.bottomNavigation.ProfileFragment
import com.mkamilmistar.gold_market.helpers.Utils

class MainActivity : AppCompatActivity() {

  private val homeFragment = HomeFragment()
  private val historyFragment = HistoryFragment()
  private val profileFragment = ProfileFragment()
  lateinit var emailPass: String
  lateinit var pwdPass: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    replaceFragment(homeFragment)

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
    emailPass = intent.getStringExtra(Utils.EMAIL).toString()
    pwdPass = intent.getStringExtra(Utils.PASSWORD).toString()
    Log.d("EMAIL", emailPass)
    val fragmentTransaction = supportFragmentManager.beginTransaction()

    val data = Bundle()
    data.putString("EMAIL", this.emailPass)
    data.putString("PASSWORD", this.pwdPass)

    fragment.arguments = data

    fragmentTransaction.replace(R.id.fragment_container, fragment)
    fragmentTransaction.commit()
  }
}
