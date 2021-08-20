package com.mkamilmistar.gold_market.data.model.response

data class CustomerPurchasesResponse(
  val id: String,
  val purchaseDate: String,
  val purchaseType: Int,
  val purchaseDetail: PurchaseDetail
)
