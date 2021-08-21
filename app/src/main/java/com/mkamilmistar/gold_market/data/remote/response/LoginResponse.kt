package com.mkamilmistar.gold_market.data.remote.response

data class LoginResponse (
  val userId: String,
  val token: String,
  val firstName: String,
  val lastName: String,
  val birthDate: String,
  val address: String,
  val status: Int,
  val username: String,
  val password: String,
  val email: String,
  )
