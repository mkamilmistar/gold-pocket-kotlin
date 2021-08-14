package com.mkamilmistar.gold_market.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithPurchases(
  @Embedded val customer: Customer?,
  @Relation(
    parentColumn = "customer_id",
    entityColumn = "customer_purchase_id"
  )
  val purchases: List<Purchase>
)
