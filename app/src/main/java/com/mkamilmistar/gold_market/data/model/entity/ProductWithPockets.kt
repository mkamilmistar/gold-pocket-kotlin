package com.mkamilmistar.gold_market.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithPockets(
  @Embedded val product: Product?,
  @Relation(
    parentColumn = "product_id",
    entityColumn = "product_pocket_id"
  )
  val pockets: List<Pocket>
)
