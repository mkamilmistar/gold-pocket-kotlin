package com.mkamilmistar.gold_market.helpers

sealed class EventResult {
  object Idle : EventResult()
  object Loading : EventResult()
  data class Success(val data: Any) : EventResult()
  data class Failed(val errorMessage: Any) : EventResult()
}
