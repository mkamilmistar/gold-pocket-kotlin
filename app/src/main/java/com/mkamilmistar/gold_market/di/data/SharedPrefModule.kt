package com.mkamilmistar.gold_market.di.data

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mkamilmistar.gold_market.utils.SharedPref
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule {
//  @Singleton
//  @Provides
//  fun provideSharedPref(application: Application): SharedPref {
//    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//    val pref = EncryptedSharedPreferences.create(
//      "SharedPref",
//      masterKeyAlias,
//      application,
//      EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//      EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
//    return SharedPref(pref)
//  }
}
