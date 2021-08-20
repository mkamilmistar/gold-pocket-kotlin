package com.mkamilmistar.gold_market.data.model.request

data class UpdatePocketRequest (
  val id: String,
  val pocketName: String,
  val pocketQty: Double,
  val customer: CreatePocketRequest.CustomerId,
  val product: CreatePocketRequest.ProductId
  )
