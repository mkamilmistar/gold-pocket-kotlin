package com.mkamilmistar.gold_market.data.model

import java.util.*

data class Purchase(
  val id: String, val purchaseDate: String,
  val purchaseType: Int, val price: Number,
  val qtyInGram: Double
) {
}
