package com.mkamilmistar.gold_market.utils

import com.mkamilmistar.mysimpleretrofit.utils.ResourceStatus

data class Resource<T>(
  val status: ResourceStatus,
  val data: T?,
  val message: String?
) {
  companion object {
    fun <T> success(data: T): Resource<T> =
      Resource(status = ResourceStatus.SUCCESS, data = data, message = null)

    fun <T> error(message: String?): Resource<T> =
      Resource(status = ResourceStatus.ERROR, data = null, message = message)

    fun <T> loading(): Resource<T> =
      Resource(status = ResourceStatus.LOADING, data = null, message = null)
  }
}
