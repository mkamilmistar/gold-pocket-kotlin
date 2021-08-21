package com.mkamilmistar.gold_market.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_products")
data class Product(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "product_id")
  val productId: Int = 0,

  @ColumnInfo(name = "name")
  val productName: String,

  @ColumnInfo(name = "price_buy")
  val productPriceBuy: Int,

  @ColumnInfo(name = "price_cell")
  val productPriceSell: Int,

  @ColumnInfo(name = "image")
  val productImage: String,

  @ColumnInfo(name = "status")
  val productStatus: Int,

  @ColumnInfo(name = "created_date")
  val createdDate: String,

  @ColumnInfo(name = "updated_date")
  val updatedDate: String,
)
