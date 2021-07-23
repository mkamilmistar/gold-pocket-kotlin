package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mkamilmistar.gold_market.R

class RegisterActivity : AppCompatActivity(), TextWatcher {

  private lateinit var firstNameText: TextInputEditText
  private lateinit var lastNameText: TextInputEditText
  private lateinit var emailRegister: TextInputEditText
  private lateinit var pwdRegister: TextInputEditText
  private lateinit var btnSignIn: TextView
  private lateinit var btnSignUp: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    btnSignUp = findViewById(R.id.btn_register)
    btnSignIn = findViewById(R.id.text_sign_in)

    btnSignUp.isEnabled = false

    firstNameText = findViewById(R.id.firstNameText)
    lastNameText = findViewById(R.id.lastNameText)
    emailRegister = findViewById(R.id.emailRegisterText)
    pwdRegister = findViewById(R.id.pwdRegisterText)

    firstNameText.addTextChangedListener(this)
    lastNameText.addTextChangedListener(this)
    emailRegister.addTextChangedListener(this)
    pwdRegister.addTextChangedListener(this)

    btnSignIn.setOnClickListener {
      Intent(this@RegisterActivity, LoginActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }

    btnSignUp.setOnClickListener {
      Intent(this@RegisterActivity, MainActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }

    val termCondition: TextView = findViewById(R.id.term_condition)
    termCondition.setOnClickListener {
      Intent(this@RegisterActivity, TermAndConditionActivity::class.java).apply {
        startActivity(this)
      }
    }
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    btnSignUp.isEnabled =
      (firstNameText.text.toString().isNotEmpty() &&
        lastNameText.text.toString().isNotEmpty() &&
        emailRegister.text.toString().isNotEmpty() &&
        emailRegister.text.toString().contains("@") &&
        pwdRegister.text.toString().isNotEmpty() &&
        pwdRegister.text.toString().length >= 6
        )
  }

  override fun afterTextChanged(s: Editable?) {
  }
}
