package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.mkamilmistar.gold_market.R
import com.mkamilmistar.gold_market.fragments.bottomNavigation.HomeFragment
import com.mkamilmistar.gold_market.helpers.Utils

class LoginActivity : AppCompatActivity() {

  private val emailData = MutableLiveData<String>()
  private val passwordData = MutableLiveData<String>()
  private val isValidData = MediatorLiveData<Boolean>().apply {
    this.value = false

    addSource(emailData) { email ->
      val password = passwordData.value
      this.value = validateForm(email, password)
    }

    addSource(passwordData) { password ->
      val email = emailData.value
      this.value = validateForm(email, password)
    }
  }

  private fun validateForm(email: String?, password: String?): Boolean {
    val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
    val isValidPassword = password != null && password.isNotBlank() && password.length >= 6
    return isValidEmail && isValidPassword
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val emailField = findViewById<TextInputLayout>(R.id.email_login)
    val pwdField = findViewById<TextInputLayout>(R.id.password_login)
    val btnSignIn: Button = findViewById(R.id.btn_sign_in)

    emailField.editText?.doOnTextChanged { text, _, _, _ ->
      emailData.value = text?.toString()
    }

    pwdField.editText?.doOnTextChanged { text, _, _, _ ->
      passwordData.value = text?.toString()
    }

    isValidData.observe(this) { isValid ->
        btnSignIn.isEnabled = isValid
    }

    val signUpActivity: TextView = findViewById(R.id.text_sign_up)
    signUpActivity.setOnClickListener {
      Intent(this, RegisterActivity::class.java).apply {
        startActivity(this)
        finish()
      }
    }

    btnSignIn.setOnClickListener {
      Intent(this, MainActivity::class.java).apply {
        putExtra(Utils.EMAIL, emailField.editText?.text.toString().trim())
        putExtra(Utils.PASSWORD, pwdField.editText?.text.toString().trim())
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
}
