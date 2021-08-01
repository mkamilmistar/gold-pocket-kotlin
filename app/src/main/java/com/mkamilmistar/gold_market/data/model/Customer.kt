package com.mkamilmistar.gold_market.data.model

data class Customer(
  val id: String, val firstName: String,
  val lastName: String, val email: String,
  val password: String
) {
  val birthDate: String = ""
  val address: String = ""
  val status: String = ""
  val username: String = ""
  val token: String = ""
}
