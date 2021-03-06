package com.mkamilmistar.gold_market.data.remote.response

data class BaseResponse<T>(
  val status: String,
  val message: String,
  val data: List<T>
)
