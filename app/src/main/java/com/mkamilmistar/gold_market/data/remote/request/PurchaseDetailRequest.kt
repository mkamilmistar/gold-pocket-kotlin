package com.mkamilmistar.gold_market.data.remote.request

data class PurchaseDetailRequest(
  val pocket: PocketRequest,
  val quantityInGram: Double
)
