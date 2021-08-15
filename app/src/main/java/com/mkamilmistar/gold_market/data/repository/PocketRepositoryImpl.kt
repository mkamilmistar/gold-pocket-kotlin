package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.utils.BusinessException

class PocketRepositoryImpl(private val db: AppDatabase): PocketRepository {

  override fun customerPockets(customerId: Int): CustomerWithPockets {
    val result = db.authDao().getCustomerPockets(customerId)
    if (!result.equals(null)) {
      return result
    } else {
      throw BusinessException("Gagal mendapatkan data Toket")
    }
  }

  override fun findPocketById(pocketId: Int): Pocket {
   return db.pocketDao().getPocketById(pocketId)
  }

  override fun addPocket(pocket: Pocket) {
    db.pocketDao().insert(pocket)
  }

  override fun deletePocket(pocketId: Int) {
    db.pocketDao().deleteById(pocketId)
  }
}
