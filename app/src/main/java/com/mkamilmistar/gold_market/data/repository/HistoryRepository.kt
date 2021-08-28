package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.remote.entity.Purchase

interface HistoryRepository {
  suspend fun customerPurchases(customerId: String): List<Purchase>?

}
