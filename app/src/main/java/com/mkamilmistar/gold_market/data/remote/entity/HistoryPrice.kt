package com.mkamilmistar.gold_market.data.remote.entity

data class HistoryPrice (
  val id: String,
  val historyDate: String,
  val priceBuy: Long,
  val priceSell: Long
  )
