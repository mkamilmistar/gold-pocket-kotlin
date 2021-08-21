package com.mkamilmistar.gold_market.data.remote.request

data class CreatePocketRequest (
  val pocketName: String,
  val customer: CustomerId,
  val product: ProductId
  ) {
  data class CustomerId(
    val id: String
  )
  data class ProductId (
    val id: String
    )
}
