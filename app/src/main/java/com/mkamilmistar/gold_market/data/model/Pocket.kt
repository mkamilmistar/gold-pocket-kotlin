package com.mkamilmistar.gold_market.data.model

data class Pocket(
  val id: String, val pocketName: String,
  val pocketQty: Number
) {
  val product: Product = Product(
    "", "", 100000, 120000, "",
    0, "", ""
  )
  val customer: Customer = Customer("", "", "", "", "")
}
