package com.mkamilmistar.gold_market.data.model.request

data class PurchaseDetailRequest(
  val pocket: PocketRequest,
  val quantityInGram: Double
)
