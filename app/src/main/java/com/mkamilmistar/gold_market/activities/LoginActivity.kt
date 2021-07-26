package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.mkamilmistar.gold_market.MainActivity
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.helpers.Utils

class LoginActivity : AppCompatActivity(), TextWatcher {

  private lateinit var emailLogin: TextInputEditText
  private lateinit var passwordLogin: TextInputEditText
  private lateinit var btnSignIn: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)


    btnSignIn = findViewById(R.id.btn_sign_in)
    emailLogin = findViewById(R.id.loginEmail)
    passwordLogin = findViewById(R.id.loginPassword)

    btnSignIn.isEnabled = false

    emailLogin.addTextChangedListener(this)
    passwordLogin.addTextChangedListener(this)

    val signUpActivity: TextView = findViewById(R.id.text_sign_up)
    signUpActivity.setOnClickListener {
      Intent(this, RegisterActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }

    btnSignIn.setOnClickListener {
      Intent(this, MainActivity::class.java).apply {
        putExtra(Utils.EMAIL, emailLogin.text.toString())
        putExtra(Utils.PASSWORD, passwordLogin.text.toString())
        startActivity(this)
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

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    btnSignIn.isEnabled =
      (emailLogin.text.toString().isNotEmpty() &&
        emailLogin.text.toString().contains("@") &&
        passwordLogin.text.toString().isNotEmpty() &&
        passwordLogin.text.toString().length >= 6
        )
  }

  override fun afterTextChanged(s: Editable?) {
  }
}
