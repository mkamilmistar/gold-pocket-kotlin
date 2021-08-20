package com.mkamilmistar.gold_market.data.model.response

data class GetCustomerLoginResponse(
  val email: String,
  val password: String,
  val address: String,
  val birthDate: String,
  val firstName: String,
  val lastName: String,
  val status: Int,
  val userId: String,
  val username: String,
  val token: String
)
