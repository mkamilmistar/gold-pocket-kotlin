package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.remote.entity.Pocket
import com.mkamilmistar.gold_market.data.remote.request.CreatePocketRequest
import com.mkamilmistar.gold_market.data.remote.request.UpdatePocketRequest
import com.mkamilmistar.gold_market.data.remote.response.*

interface PocketRepository {
  suspend fun findPocketById(pocketId: String): Pocket?
  suspend fun addPocket(request: CreatePocketRequest) : Pocket?
  suspend fun deletePocket(pocketId: String): DeletePocketResponse?
  suspend fun customerPockets(customerId: String): List<Pocket>?
  suspend fun updatePocket(request: UpdatePocketRequest): Pocket?
}
