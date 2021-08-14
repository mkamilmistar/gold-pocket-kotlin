package com.mkamilmistar.gold_market.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithPockets(
  @Embedded val customer: Customer?,
  @Relation(
    parentColumn = "customer_id",
    entityColumn = "customer_pocket_id"
  )
  val pockets: List<Pocket>
)
