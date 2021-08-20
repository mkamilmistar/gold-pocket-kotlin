package com.mkamilmistar.gold_market.data.model.response

data class PurchaseResponse(
  val purchaseId: Int = 0,
  val purchaseDate: String,
  val purchaseType: Int,
  val purchaseDetail: PurchaseDetail
)
