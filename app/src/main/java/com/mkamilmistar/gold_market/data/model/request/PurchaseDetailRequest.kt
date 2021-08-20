package com.mkamilmistar.gold_market.data.model.request

data class PurchaseDetailRequest(
  val pocketRequest: PocketRequest,
  val quantityInGram: Int
)
