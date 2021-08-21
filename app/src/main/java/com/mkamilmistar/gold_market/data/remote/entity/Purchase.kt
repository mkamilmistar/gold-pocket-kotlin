package com.mkamilmistar.gold_market.data.remote.entity

data class Purchase(
  val id: String = "",
  val purchaseDate: String = "",
  val purchaseType: Long,
  val customer: Customer,
  val purchaseDetails: List<PurchaseDetail>
)
