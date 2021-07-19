package com.mkamilmistar.gold_market.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mkamilmistar.gold_market.R


class OnBoardingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_on_boarding)

    val btnGetStarted: Button = findViewById(R.id.btn_get_started)
    btnGetStarted.setOnClickListener {
      Intent(this@OnBoardingActivity, LoginActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }
  }
}
