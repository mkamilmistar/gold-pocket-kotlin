package com.mkamilmistar.gold_market.data.repository

import android.util.Log
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.CustomerWithPockets
import com.mkamilmistar.gold_market.data.model.request.LoginRequest
import com.mkamilmistar.gold_market.utils.BusinessException

class CustomerRepositoryImpl(
  private val db: AppDatabase
) : CustomerRepository {


  override fun customerLogin(loginRequest: LoginRequest): Customer {
    val data = db.customerDao().getDataLogin(loginRequest.email, loginRequest.password)
    if (!data.equals(null)) {
      return data
    } else {
      throw BusinessException("Email or Password incorrect")
    }
  }

  override fun getCustomerById(customerId: Int): Customer {
    val data = db.customerDao().getDataCustomerById(customerId)
    if (!data.equals(null)) {
      return data
    } else {
      throw BusinessException("Data not found")
    }
  }

  override fun register(customer: Customer): Long {
    return db.customerDao().insert(customer)
  }

  override fun customerPockets(customerId: Int): CustomerWithPockets {
    val result = db.customerDao().getCustomerPockets(customerId)
    if (!result.equals(null)) {
      return result
    } else {
      throw BusinessException("Gagal mendapatkan data Toket")
    }
  }

  companion object {
    var customerDB = Customer(0, "Melia", "Suspariana", "melia@gmail.com", "melia","melia123", "", "", "", "")
  }
  val customerDBImport
    get() = customerDB
}
