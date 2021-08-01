package com.mkamilmistar.gold_market.data.model

data class Product(
  val id: String, val productName: String,
  val productPriceBuy: Number, val productPriceSell: Number,
  val productImage: String, val productStatus: Number,
  val createdDate: String,
  val updatedDate: String,
) {
  val historyPrices: ProductHistory = ProductHistory("", "", 10000, 120000)
}
