package com.mkamilmistar.gold_market

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    @Suppress("DEPRECATION")
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN)
    setContentView(R.layout.activity_splash_screen)

    val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
    val botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

    val splashLogo: ImageView = findViewById(R.id.splash_logo)
    val logoText: TextView = findViewById(R.id.logo_text)
    val sloganText: TextView = findViewById(R.id.slogan_text)

    splashLogo.startAnimation(topAnim)
    logoText.startAnimation(botAnim)
    sloganText.startAnimation(botAnim)

    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
      startActivity(intent)
      finish()
    }, 3000)
  }
}
