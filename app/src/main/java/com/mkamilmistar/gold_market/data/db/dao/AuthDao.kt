package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mkamilmistar.gold_market.data.db.entity.Customer
import com.mkamilmistar.gold_market.data.db.entity.CustomerWithPockets

@Dao
interface AuthDao : BaseDao<Customer> {
  @Query("SELECT * FROM m_customers WHERE email = :email AND password = :password")
  fun getDataLogin(email: String, password: String): Customer

  @Query("SELECT * FROM m_customers WHERE customer_id = :customerId")
  fun getDataCustomerById(customerId: Int): Customer

  @Transaction
  @Query("SELECT * FROM m_customers WHERE customer_id = :id")
  fun getCustomerPockets(id: Int): CustomerWithPockets
}
