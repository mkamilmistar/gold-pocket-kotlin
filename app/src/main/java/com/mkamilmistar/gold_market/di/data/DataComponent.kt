package com.mkamilmistar.gold_market.di.data

import android.app.Application
import android.content.Context
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.utils.SharedPref
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ApiModule::class,
    DbModule::class,
    SharedPrefModule::class
  ]
)
interface DataComponent {
  fun provideRetrofit(): Retrofit
  fun provideDatabase(): AppDatabase
  fun provideSharedPref(): SharedPref

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(app: Application): Builder
    fun build(): DataComponent
  }
}
