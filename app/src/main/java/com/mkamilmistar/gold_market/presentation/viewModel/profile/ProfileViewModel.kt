package com.mkamilmistar.gold_market.presentation.viewModel.profile

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.repository.ProfileRepository
import com.mkamilmistar.gold_market.helpers.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel(private val profileRepo: ProfileRepository) : ViewModel() {
  private var _customerLiveData = MutableLiveData<EventResult<Customer>>()
  val customerLivedata: LiveData<EventResult<Customer>>
    get() {
      return _customerLiveData
    }

  fun getCustomerById(customerId: Int) {
    _customerLiveData.postValue(EventResult.Loading)
    Handler(Looper.getMainLooper()).postDelayed({
      viewModelScope.launch(Dispatchers.IO) {
        try {
          val customerData = profileRepo.getCustomerById(customerId)
          _customerLiveData.postValue(EventResult.Success(data = customerData))
        } catch (e: Exception) {
          _customerLiveData.postValue(
            e.localizedMessage?.toString()?.let { EventResult.Failed(it) })
        }
      }
    }, 1000)
  }
}
