package com.mkamilmistar.gold_market.data.model.request

data class PurchaseRequest(
  val purchaseId: Int = 0,
  val purchaseDate: String,
  val purchaseType: Int,
  val price: Int = 0,
  val qtyInGram: Double,
)
