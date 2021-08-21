package com.mkamilmistar.gold_market.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "m_customers")
data class Customer(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "customer_id")
  val customerId: Int = 0,

  @ColumnInfo(name = "first_name") val firstName: String,
  @ColumnInfo(name = "last_name") val lastName: String,
  @ColumnInfo(name = "email") val email: String,
  @ColumnInfo(name = "username") val username: String,
  @ColumnInfo(name = "password") val password: String,
  @ColumnInfo(name = "status") val status: String?,
  @ColumnInfo(name = "birth_date") val birthDate: String?,
  @ColumnInfo(name = "address") val address: String?,
  @ColumnInfo(name = "token") val token: String?,

)
