package com.mkamilmistar.gold_market.data.remote.request

data class RegisterRequest (
  val firstName: String,
  val lastName: String,
  val email: String,
  val username: String,
  val password: String,
  val status: String?,
  val birthDate: String?,
  val address: String?,
)
