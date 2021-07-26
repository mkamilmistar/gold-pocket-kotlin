package com.mkamilmistar.gold_market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
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
    binding.bottomNavigation.setupWithNavController(navController)
  }

  private fun visibilityBotNavBar(navController: NavController) {
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
