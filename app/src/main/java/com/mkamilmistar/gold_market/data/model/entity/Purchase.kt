package com.mkamilmistar.gold_market.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_purchases")
data class Purchase(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "purchase_id")
  val purchaseId: Int = 0,

  @ColumnInfo(name = "purchase_date")
  val purchaseDate: String,

  @ColumnInfo(name = "type")
  val purchaseType: Int,

  @ColumnInfo(name = "price")
  val price: Int = 0,

  @ColumnInfo(name = "qty_in_gram")
  val qtyInGram: Double,

  @ColumnInfo(name = "customer_purchase_id")
  val customerPurchaseId: Long,

  @ColumnInfo(name = "pocket_purchase_id")
  val pocketPurchaseId: Long
)
