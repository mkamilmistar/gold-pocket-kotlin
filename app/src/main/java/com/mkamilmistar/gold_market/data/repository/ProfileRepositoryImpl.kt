package com.mkamilmistar.gold_market.data.repository

import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.utils.BusinessException

class ProfileRepositoryImpl(private val db: AppDatabase): ProfileRepository {

  override fun getCustomerById(customerId: Int): Customer {
    val data = db.authDao().getDataCustomerById(customerId)
    if (!data.equals(null)) {
      return data
    } else {
      throw BusinessException("Data not found")
    }
  }
}
