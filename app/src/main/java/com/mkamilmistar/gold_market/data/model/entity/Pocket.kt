package com.mkamilmistar.gold_market.data.model.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_pockets")
data class Pocket(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "pocket_id")
  val pocketId: Int = 0,

  @ColumnInfo(name = "name")
  val pocketName: String,

  @ColumnInfo(name = "qty")
  @Nullable var pocketQty: Int = 0,

  @ColumnInfo(name = "customer_pocket_id")
  val customerPocketId: Long = 0,

  @ColumnInfo(name = "product_pocket_id")
  val productPocketId: Long = 1
)
