package com.mkamilmistar.gold_market.data.model.response

import java.util.*

data class LoginResponse (
  val address: String,
  val birthDate: String,
  val email: String,
  val firstName: String,
  val lastName: String,
  val password: String,
  val status: Any,
  val token: String,
  val userId: String,
  val username: String
  )
