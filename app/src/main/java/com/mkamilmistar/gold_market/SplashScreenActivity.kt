package com.mkamilmistar.gold_market

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

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

    splash_logo.startAnimation(topAnim)
    logo_text.startAnimation(botAnim)
    slogan_text.startAnimation(botAnim)

    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
      startActivity(intent)
      finish()
    }, 3000)
  }
}
