package com.mkamilmistar.gold_market.data.model.response

data class Pocket (
  val id: String = "",
  val pocketName: String,
  var pocketQty: Double,
  val customer: Customer,
  val product: Product
  )
