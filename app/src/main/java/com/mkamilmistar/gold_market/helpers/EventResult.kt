package com.mkamilmistar.gold_market.helpers

sealed class EventResult<out T> {
  object Idle : EventResult<Nothing>()
  object Loading : EventResult<Nothing>()
  data class Success<T>(val data: T) : EventResult<T>()
  data class Failed(val errorMessage: Any?) : EventResult<Nothing>()
  fun toData(): T? = if(this is Success) this.data else null
}
