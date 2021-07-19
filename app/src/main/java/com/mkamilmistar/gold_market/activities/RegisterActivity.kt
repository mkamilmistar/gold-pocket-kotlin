package com.mkamilmistar.gold_market.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.mkamilmistar.gold_market.R

class RegisterActivity : AppCompatActivity() {


  private val firstNameData = MutableLiveData<String>()
  private val lastNameData = MutableLiveData<String>()
  private val emailData = MutableLiveData<String>()
  private val passwordData = MutableLiveData<String>()
  private val isValidData = MediatorLiveData<Boolean>().apply {
    this.value = false

    addSource(lastNameData) { firstName ->
      val lastName = lastNameData.value
      val email = emailData.value
      val password = passwordData.value
      this.value = validateForm(firstName, lastName, email, password)
    }

    addSource(firstNameData) { lastName ->
      val firstName = firstNameData.value
      val email = emailData.value
      val password = passwordData.value
      this.value = validateForm(firstName, lastName, email, password)
    }

    addSource(emailData) { email ->
      val firstName = firstNameData.value
      val lastName = lastNameData.value
      val password = passwordData.value
      this.value = validateForm(firstName, lastName, email, password)
    }

    addSource(passwordData) { password ->
      val firstName = firstNameData.value
      val lastName = lastNameData.value
      val email = emailData.value
      this.value = validateForm(firstName, lastName, email, password)
    }
  }

  private fun validateForm(
    firstName: String?,
    lastName: String?,
    email: String?,
    password: String?
  ): Boolean {
    val isValidateFirstName = firstName != null && firstName.isNotBlank()
    val isValidateLastName = lastName != null && lastName.isNotBlank()
    val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
    val isValidPassword = password != null && password.isNotBlank() && password.length >= 6
    return isValidateFirstName && isValidateLastName && isValidEmail && isValidPassword
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    val firstNameField = findViewById<TextInputLayout>(R.id.first_name_register)
    val lastNameField = findViewById<TextInputLayout>(R.id.last_name_register)
    val emailField = findViewById<TextInputLayout>(R.id.email_register)
    val pwdField = findViewById<TextInputLayout>(R.id.password_register)
    val btnSignUp: Button = findViewById(R.id.btn_register)

    firstNameField.editText?.doOnTextChanged { text, _, _, _ ->
      firstNameData.value = text?.toString()
    }

    lastNameField.editText?.doOnTextChanged { text, _, _, _ ->
      lastNameData.value = text?.toString()
    }

    emailField.editText?.doOnTextChanged { text, _, _, _ ->
      emailData.value = text?.toString()
    }

    pwdField.editText?.doOnTextChanged { text, _, _, _ ->
      passwordData.value = text?.toString()
    }

    isValidData.observe(this) { isValid ->
      btnSignUp.isEnabled = isValid
    }

    val signIn: TextView = findViewById(R.id.text_sign_in)
    signIn.setOnClickListener {
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
}
