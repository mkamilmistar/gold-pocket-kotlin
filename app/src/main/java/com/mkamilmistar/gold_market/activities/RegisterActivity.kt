package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mkamilmistar.gold_market.R

class RegisterActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    val signIn: TextView = findViewById(R.id.text_sign_in)
    signIn.setOnClickListener {
      val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
      startActivity(intent)
      finish()
    }

    val btnSignUp: Button = findViewById(R.id.btn_register)
    btnSignUp.setOnClickListener {
      val intent = Intent(this@RegisterActivity, MainActivity::class.java)
      startActivity(intent)
      finish()
    }

    val termCondition: TextView = findViewById(R.id.term_condition)
    termCondition.setOnClickListener {
      Intent(this@RegisterActivity, TermAndConditionActivity::class.java).apply {
        startActivity(this)
      }
    }
  }
}
