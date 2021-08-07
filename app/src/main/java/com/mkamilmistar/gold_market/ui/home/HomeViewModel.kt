package com.mkamilmistar.gold_market.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkamilmistar.gold_market.data.model.Customer
import com.mkamilmistar.gold_market.data.model.Pocket
import com.mkamilmistar.gold_market.data.model.ProductHistory
import com.mkamilmistar.gold_market.data.model.Purchase
import com.mkamilmistar.gold_market.data.repository.CustomerRepository
import com.mkamilmistar.gold_market.data.repository.PocketRepository
import com.mkamilmistar.gold_market.data.repository.ProductHistoryRepository
import com.mkamilmistar.gold_market.data.repository.PurchaseRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import java.lang.Exception

class HomeViewModel(
  private val customerRepository: CustomerRepository,
  private val purchaseRepository: PurchaseRepository,
  private val pocketRepository: PocketRepository,
  private val productHistoryRepository: ProductHistoryRepository
) : ViewModel() {
  private var _customerLiveData = MutableLiveData<EventResult<Customer>>(EventResult.Idle)
  val customerLiveData: LiveData<EventResult<Customer>>
    get() = _customerLiveData

  private var _pocketLiveData = MutableLiveData<EventResult<Pocket>>(EventResult.Idle)
  val pocketLiveData: LiveData<EventResult<Pocket>>
    get() = _pocketLiveData

  private var _productHistoryLiveData = MutableLiveData<EventResult<List<ProductHistory>>>(EventResult.Idle)
  val productHistoryLiveData: LiveData<EventResult<List<ProductHistory>>>
    get() = _productHistoryLiveData

  fun start(email: String, pwd: String, pocketPosition: Int) {
    updateDataCustomer(email, pwd)
    updateProductHistory()
    updatePocketActive(pocketPosition)
  }

  private fun getCustomerFromRepository(email: String, pwd: String): Customer {
    return customerRepository.getCustomer(email, pwd)
  }

  private fun updateDataCustomer(email: String, pwd: String) {
    _customerLiveData.value = EventResult.Loading
    try {
      val customer: Customer = getCustomerFromRepository(email, pwd)
      _customerLiveData.value = EventResult.Success(customer)
    } catch (e: Exception) {
      _customerLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  private fun updatePocketActive(pocketPosition: Int) {
    _pocketLiveData.value = EventResult.Loading
    try {
      val pocketById: Pocket = pocketRepository.findPocket(pocketPosition)
      _pocketLiveData.value = EventResult.Success(pocketById)
    } catch (e: Exception) {
      _pocketLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  private fun updateProductHistory() {
    _pocketLiveData.value = EventResult.Loading
    try {
      val productHistories: List<ProductHistory> = productHistoryRepository.findAllProductHistory()
      _productHistoryLiveData.value = EventResult.Success(productHistories)
    } catch (e: Exception) {
      _productHistoryLiveData.value = e.localizedMessage?.toString()?.let { EventResult.Failed(it) }
    }
  }

  fun purchaseProduct(purchase: Purchase) {
    purchaseRepository.addPurchase(purchase)
  }

}
