package com.mkamilmistar.gold_market

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    @Suppress("DEPRECATION")
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main)

    val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
    val botAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

    splash_logo.startAnimation(topAnim)
    logo_text.startAnimation(botAnim)
    slogan_text.startAnimation(botAnim)
  }
}
