package com.mkamilmistar.gold_market.data.model.response

data class BaseResponse<T>(
  val status: String,
  val message: String,
  val data: List<T>
)
