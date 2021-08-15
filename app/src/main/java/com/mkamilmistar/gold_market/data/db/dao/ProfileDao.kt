package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.mkamilmistar.gold_market.data.model.entity.Customer

@Dao
interface ProfileDao: BaseDao<Customer> {
  @Query("SELECT * FROM m_customers WHERE customer_id = :customerId")
  fun getDataCustomerById(customerId: Int): Customer
}
