package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Pocket

interface PocketRepository {
  fun findAllPocket(): List<Pocket>
  fun findPocket(position: Int): Pocket
  fun addPocket(pocket: Pocket)
  fun deletePocket(position: Int)
}
