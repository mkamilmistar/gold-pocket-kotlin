package com.mkamilmistar.gold_market.di.data

import android.app.Application
import androidx.room.Room
import com.mkamilmistar.gold_market.data.db.AppDatabase
import com.mkamilmistar.gold_market.data.db.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
  @Singleton
  @Provides
  fun provideRoomDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
      application,
      AppDatabase::class.java,
      "gold_pocket"
    ).build()
  }
}
