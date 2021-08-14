package com.mkamilmistar.gold_market.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPurchases

@Dao
interface CustomerDao: BaseDao<Customer> {
  @Query("SELECT * FROM m_customers WHERE email = :email AND password = :password")
  fun getDataLogin(email: String, password: String) : Customer

  @Transaction
  @Query("SELECT * FROM m_customers WHERE customer_id = :id")
  fun getCustomerPockets(id: Int): CustomerWithPockets
}
