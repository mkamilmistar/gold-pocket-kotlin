package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mkamilmistar.gold_market.R

class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val signUpActivity: TextView = findViewById(R.id.text_sign_up)
    signUpActivity.setOnClickListener {
      val intent = Intent(this, RegisterActivity::class.java)
      startActivity(intent)
      finish()
    }

    val btnSignIn: Button = findViewById(R.id.btn_sign_in)
    btnSignIn.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      finish()
    }
  }
}
