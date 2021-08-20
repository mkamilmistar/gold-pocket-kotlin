package com.mkamilmistar.gold_market.data.model.response

data class Product (
  val id: String,
  val productName: String,
  val productPriceBuy: Long,
  val productPriceSell: Long,
  val productImage: String,
  val productStatus: Long,
  val historyPrices: List<HistoryPrice>,
  val createdDate: String,
  val updatedDate: String
  )
