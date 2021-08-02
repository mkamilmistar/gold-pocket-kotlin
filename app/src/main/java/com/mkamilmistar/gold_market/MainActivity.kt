package com.mkamilmistar.gold_market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mkamilmistar.gold_market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController
  private lateinit var navHostFragment: NavHostFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    navHostFragment =
      supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    navController = navHostFragment.findNavController()
    visibilityBotNavBar(navController)
    binding.apply {
      bottomNavigation.setOnItemSelectedListener {
        when (it.itemId) {
          R.id.homeFragment -> {
            navController.navigate(
              R.id.homeFragment, null,
              NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
            ); true
          }
          R.id.historyFragment -> {
            navController.navigate(
              R.id.historyFragment, null,
              NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
            ); true
          }
          R.id.settingFragment -> {
            navController.navigate(
              R.id.settingFragment, null,
              NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
            ); true
          }
          else -> {
            false
          }
        }
      }
    }
  }

  private fun visibilityBotNavBar(navController: NavController) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
      binding.apply {
        when (destination.id) {
          R.id.homeFragment,
          R.id.historyFragment,
          R.id.settingFragment -> bottomNavigation.visibility = View.VISIBLE
          else -> bottomNavigation.visibility = View.GONE
        }
      }
    }
  }
}
