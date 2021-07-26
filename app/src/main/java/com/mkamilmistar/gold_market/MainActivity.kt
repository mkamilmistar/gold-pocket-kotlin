package com.mkamilmistar.gold_market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
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
    visibilityNavElements(navController)
    binding.bottomNavigation.setupWithNavController(navController)
  }

  private fun visibilityNavElements(navController: NavController) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
      binding.apply {
        when (destination.id) {
          R.id.splashScreenFragment,
          R.id.forgetPasswordFragment,
          R.id.termAndConditionFragment,
          R.id.onBoardingFragment,
          R.id.loginFragment,
          R.id.registerFragment -> bottomNavigation.visibility = View.GONE
          else -> bottomNavigation.visibility = View.VISIBLE
        }
      }
    }
  }
}
