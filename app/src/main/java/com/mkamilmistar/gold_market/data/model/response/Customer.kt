package com.mkamilmistar.gold_market.data.model.response

data class Customer(
  val id: String,
  val firstName: String,
  val lastName: String,
  val birthDate: String,
  val address: String,
  val status: Long,
  val username: String,
  val password: String,
  val email: String,
  val pockets: List<Pocket>? = null
)
