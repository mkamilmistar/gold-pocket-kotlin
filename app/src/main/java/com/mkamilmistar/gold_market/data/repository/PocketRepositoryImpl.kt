package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Pocket

class PocketRepositoryImpl(private val db: AppDatabase): PocketRepository {
  override fun findAllPocket(): List<Pocket> {
    return pocketDB
  }

  override fun findPocketById(pocketId: Int): Pocket {
   return db.pocketDao().getPocketById(pocketId)
  }

//  override fun findPocket(position: Int): Pocket {
//    return pocketDB[position]
//  }

  override fun addPocket(pocket: Pocket) {
    db.pocketDao().insert(pocket)
  }

  override fun deletePocket(pocketId: Int) {
//    db.pocketDao().delete(pocket)
    db.pocketDao().deleteById(pocketId)
  }

  companion object {
    val pocketDB: MutableList<Pocket> = mutableListOf(
      Pocket(1, "Gold Pocket", 10, 1, 1),
      Pocket(2, "Platinum Pocket", 0, 1, 1),
      Pocket(3, "Silver Pocket", 0, 1, 1),
    )
  }
  val pocketDBImport
    get() = pocketDB

}
