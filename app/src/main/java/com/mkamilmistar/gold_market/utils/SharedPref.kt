package com.mkamilmistar.gold_market.utils

import android.content.SharedPreferences

class SharedPref(private val sharedPref: SharedPreferences) {

  fun save(KEY_NAME: String, text: String) {
    val saveData : SharedPreferences.Editor = sharedPref.edit()
    saveData.putString(KEY_NAME, text)
    saveData.apply()
  }

  fun retrieved(KEY_NAME: String): String? {
    return sharedPref.getString(KEY_NAME, null)
  }

  fun clear() {
    return sharedPref.edit().clear().apply()
  }

}
