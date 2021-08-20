package com.mkamilmistar.gold_market.data.model.response

data class PurchaseDetail (
  val id: String = "",
  val product: Product,
  val price: Long = 0,
  val quantityInGram: Double,
  val pocket: Pocket
  )
