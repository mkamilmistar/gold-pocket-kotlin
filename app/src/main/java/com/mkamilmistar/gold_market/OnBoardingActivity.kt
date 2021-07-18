package com.mkamilmistar.gold_market

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class OnBoardingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    @Suppress("DEPRECATION")
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    setContentView(R.layout.activity_on_boarding)

    val btnGetStarted: Button = findViewById(R.id.btn_get_started)
    btnGetStarted.setOnClickListener {
      val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}
