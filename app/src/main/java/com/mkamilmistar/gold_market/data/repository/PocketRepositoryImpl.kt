package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.model.Pocket

class PocketRepositoryImpl: PocketRepository {
  override fun findAllPocket(): List<Pocket> {
    return pocketDB
  }

  override fun findPocket(position: Int): Pocket {
    return pocketDB[position]
  }

  override fun addPocket(pocket: Pocket) {
    pocketDB.add(pocket)
  }

  override fun deletePocket(position: Int) {
    pocketDB.removeAt(position)
  }

  companion object {
    val pocketDB: MutableList<Pocket> = mutableListOf(
      Pocket("pocket-1", "Gold Pocket", 0),
      Pocket("pocket-2", "Platinum Pocket", 0),
      Pocket("pocket-3", "Silver Pocket", 0),
    )
  }

}
