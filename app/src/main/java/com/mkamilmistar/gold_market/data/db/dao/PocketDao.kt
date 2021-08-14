package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mkamilmistar.gold_market.data.model.entity.Pocket

@Dao
interface PocketDao: BaseDao<Pocket> {
  @Query("SELECT * FROM m_pockets where pocket_id = :pocketId")
  fun getPocketById(pocketId: Int): Pocket
}
