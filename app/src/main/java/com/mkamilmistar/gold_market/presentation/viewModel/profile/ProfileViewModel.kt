package com.mkamilmistar.gold_market.presentation.viewModel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.model.response.Customer
import com.mkamilmistar.gold_market.data.repository.ProfileRepository
import com.mkamilmistar.mysimpleretrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepo: ProfileRepository) : ViewModel() {
  private var _customerLiveData = MutableLiveData<Resource<Customer>>()
  val customerLivedata: LiveData<Resource<Customer>>
    get() {
      return _customerLiveData
    }

  fun getCustomerById(customerId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      _customerLiveData.postValue(Resource.loading())
      val response = profileRepo.getCustomerById(customerId)
      if (response != null) {
        _customerLiveData.postValue(Resource.success(data = response))
      } else {
        _customerLiveData.postValue(Resource.error(message = response))
      }
    }
  }
}
