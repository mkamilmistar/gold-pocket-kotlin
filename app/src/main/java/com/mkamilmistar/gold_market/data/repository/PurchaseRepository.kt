package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.remote.request.PurchaseRequest
import com.mkamilmistar.gold_market.data.remote.entity.Purchase
import com.mkamilmistar.gold_market.data.remote.response.PurchaseResponse

interface PurchaseRepository {
  suspend fun addPurchase(customerId: String, purchase: PurchaseRequest): PurchaseResponse?
}
