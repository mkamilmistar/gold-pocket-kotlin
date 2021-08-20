package com.mkamilmistar.gold_market.utils

import android.R
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mkamilmistar.gold_market.BaseApplication
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
object Utils {
  const val CUSTOMER_ID = "CUSTOMER_ID"
  const val POCKET_ID = "POCKET_ID"
  const val PRODUCT_ID = "PRODUCT_ID"

  @JvmStatic
  fun currencyFormatter(currency: Number): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")

    return format.format(currency)
  }

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

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String) : String {
  var formattedDate = ""
  val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
  sdf.timeZone = TimeZone.getTimeZone("UTC")

  try {
      val parseDate = sdf.parse(date)
      formattedDate = SimpleDateFormat("dd MMMM yyyy").format(parseDate)
  } catch (e: ParseException) {
    e.printStackTrace()
  }
  return formattedDate
}


fun showOKDialog(context: Context?, title: String?, message: String?) {
  val builder: AlertDialog.Builder? = context?.let { AlertDialog.Builder(it) }
  builder?.setTitle(title)
  builder?.setMessage(message)
  builder?.setPositiveButton(R.string.ok, null)
  builder?.show()
}
