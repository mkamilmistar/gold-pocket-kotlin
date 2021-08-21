package com.mkamilmistar.gold_market.data.remote.request

data class PurchaseRequest(
  val purchaseType: Int,
  val purchaseDetails: List<PurchaseDetailRequest>
)
