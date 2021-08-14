package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.Pocket

interface PocketRepository {
  fun findAllPocket(): List<Pocket>
  fun findPocketById(pocketId: Int): Pocket
//  fun findPocket(position: Int): Pocket
  fun addPocket(pocket: Pocket)
  fun deletePocket(pocketId: Int)
}
