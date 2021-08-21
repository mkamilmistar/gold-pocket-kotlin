package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.mkamilmistar.gold_market.data.db.entity.Pocket

@Dao
interface PocketDao: BaseDao<Pocket> {
  @Query("SELECT * FROM m_pockets where pocket_id = :pocketId")
  fun getPocketById(pocketId: Int): Pocket

  @Query("DELETE FROM m_pockets where pocket_id = :pocketId")
  fun deleteById(pocketId: Int)

  @Query("SELECT * FROM m_pockets where customer_pocket_id = :customerId AND pocket_id = :pocketId")
  fun getPocketByCustomerId(customerId: Int, pocketId: Int): Pocket
}
