package com.mkamilmistar.gold_market.utils

import androidx.appcompat.app.AppCompatActivity
import com.mkamilmistar.gold_market.BaseApplication

object Utils {
  const val EMAIL = "EMAIL"
  const val PASSWORD = "PASSWORD"
}

val AppCompatActivity.baseApp: BaseApplication
  get() = (application as BaseApplication)

class BusinessException(message:String): Exception(message)

fun getRandomString(length: Int) : String {
  val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
  return (1..length)
    .map { allowedChars.random() }
    .joinToString("")
}
