package com.mkamilmistar.gold_market.presentation.viewModel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkamilmistar.gold_market.data.db.entity.Pocket
import com.mkamilmistar.gold_market.data.remote.request.LoginRequest
import com.mkamilmistar.gold_market.data.remote.request.RegisterRequest
import com.mkamilmistar.gold_market.data.remote.response.LoginResponse
import com.mkamilmistar.gold_market.data.remote.response.RegisterResponse
import com.mkamilmistar.gold_market.data.repository.AuthRepository
import com.mkamilmistar.gold_market.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

  private var _customerLiveData = MutableLiveData<Resource<LoginResponse>>()
  val customerLivedata: LiveData<Resource<LoginResponse>>
    get() {
      return _customerLiveData
    }

  private var _successRegister = MutableLiveData<Resource<RegisterResponse>>()
  val successRegister: LiveData<Resource<RegisterResponse>>
    get() {
      return _successRegister
    }

  fun customerLogin(loginRequest: LoginRequest) {
    viewModelScope.launch(Dispatchers.IO) {
      _customerLiveData.postValue(Resource.loading())
      val response = authRepository.login(loginRequest)
      if (response != null) {
        _customerLiveData.postValue(Resource.success(data = response))
      } else {
        _customerLiveData.postValue(Resource.error(message = response))
      }
    }
  }

  fun registerCustomer(request: RegisterRequest) {
    val commonPocket = Pocket(
      pocketName = "Tabungan Hari Tua",
      productPocketId = 1
    )
    viewModelScope.launch(Dispatchers.IO) {
      _successRegister.postValue(Resource.loading())
      val response = authRepository.register(request)
      if (response != null) {
        _successRegister.postValue(Resource.success(data = response))
      } else {
        _successRegister.postValue(Resource.error(message = response))
      }
    }
  }
}
