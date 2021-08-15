package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.Pocket

interface PocketRepository {
  fun findPocketById(pocketId: Int): Pocket
  fun addPocket(pocket: Pocket)
  fun deletePocket(pocketId: Int)
  fun customerPockets(customerId: Int): CustomerWithPockets
}
