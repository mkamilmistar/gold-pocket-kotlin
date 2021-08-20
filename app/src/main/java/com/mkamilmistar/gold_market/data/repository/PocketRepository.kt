package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.model.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.model.response.*

interface PocketRepository {
  suspend fun findPocketById(pocketId: String): Pocket?
  suspend fun addPocket(request: CreatePocketRequest) : Pocket?
  suspend fun deletePocket(pocketId: String): DeletePocketResponse?
  suspend fun customerPockets(customerId: String): List<Pocket>?
//  suspend fun getPocketByCustomerAndPocketId(customerId: Int, pocketId: Int): Pocket
  suspend fun updatePocket(request: UpdatePocketRequest): Pocket?
}
