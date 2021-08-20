package com.mkamilmistar.gold_market.data.model.response

data class ProductResponse(
  val id: String,
  val productName: String,
  val productPriceBuy: Int,
  val productPriceSell: Int,
  val productImage: String,
  val productStatus: Int
)
