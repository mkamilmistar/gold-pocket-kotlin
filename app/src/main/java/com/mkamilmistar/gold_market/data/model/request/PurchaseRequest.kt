package com.mkamilmistar.gold_market.data.model.request


data class PurchaseRequest(
  val purchaseType: Int,
  val purchaseDetails: List<PurchaseDetailRequest>
)
