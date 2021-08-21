package com.mkamilmistar.gold_market.data.remote.response

data class RegisterResponse(
  val isSuccess: Boolean,
  val token: String,
  val id: String
)
