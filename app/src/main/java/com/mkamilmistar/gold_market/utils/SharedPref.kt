package com.mkamilmistar.gold_market.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
  private val sharedPref: SharedPreferences =
    context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)

  fun save(KEY_NAME: String, text: String) {
    val saveData : SharedPreferences.Editor = sharedPref.edit()
    saveData.putString(KEY_NAME, text)
    saveData.apply()
  }

  fun retrived(KEY_NAME: String): String? {
    return sharedPref.getString(KEY_NAME, null)
  }

}
