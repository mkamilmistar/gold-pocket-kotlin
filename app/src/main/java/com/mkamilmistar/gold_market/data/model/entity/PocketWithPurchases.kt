package com.mkamilmistar.gold_market.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PocketWithPurchases(
  @Embedded val pocket: Pocket?,
  @Relation(
    parentColumn = "pocket_id",
    entityColumn = "pocket_purchase_id"
  )
  val purchase: List<Purchase>
)
