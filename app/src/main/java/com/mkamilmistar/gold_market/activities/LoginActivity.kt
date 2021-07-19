package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.mkamilmistar.gold_market.R

class LoginActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)



    val signUpActivity: TextView = findViewById(R.id.text_sign_up)
    signUpActivity.setOnClickListener {
      Intent(this, RegisterActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }

    val btnSignIn: Button = findViewById(R.id.btn_sign_in)
    val emailField: TextInputLayout = findViewById(R.id.email_login)
    val pwdField: TextInputLayout = findViewById(R.id.password_login)
    btnSignIn.setOnClickListener {
      Intent(this, MainActivity::class.java).apply {
        startActivity(this)
        putExtra(EMAIL, emailField.toString())
        putExtra(PASSWORD, pwdField.toString())
        finish()
      }
    }

    val btnForget: Button = findViewById(R.id.btn_forget)
    btnForget.setOnClickListener {
      Intent(this@LoginActivity, ForgetPasswordActivity::class.java).apply {
        startActivity(this)
      }
    }
  }

  companion object {
    const val EMAIL = "EMAIL"
    const val PASSWORD = "PASSWORD"
  }
}
