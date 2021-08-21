package com.mkamilmistar.gold_market.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SharedPref(context: Context) {
  private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
  private val sharedPref: SharedPreferences = EncryptedSharedPreferences.create(
    "SharedPref",
    masterKeyAlias,
    context,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
  )

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
