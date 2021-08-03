package com.mkamilmistar.gold_market.utils

import android.R
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mkamilmistar.gold_market.BaseApplication
import java.text.NumberFormat
import java.util.*
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

fun currencyFormatter(currency: Number): String {
  val format: NumberFormat = NumberFormat.getCurrencyInstance()
  format.maximumFractionDigits = 0
  format.currency = Currency.getInstance("IDR")

  return format.format(currency)
}


fun showOKDialog(context: Context?, title: String?, message: String?) {
  val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
  builder?.setTitle(title)
  builder?.setMessage(message)
  builder?.setPositiveButton(R.string.ok, null)
  builder?.show()
}
