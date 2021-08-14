package com.mkamilmistar.gold_market.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mkamilmistar.gold_market.data.db.dao.CustomerDao
import com.mkamilmistar.gold_market.data.db.dao.PocketDao
import com.mkamilmistar.gold_market.data.db.dao.PurchaseDao
import com.mkamilmistar.gold_market.data.model.entity.Customer
import com.mkamilmistar.gold_market.data.model.entity.Pocket
import com.mkamilmistar.gold_market.data.model.entity.Purchase

@Database(entities = [Customer::class, Pocket::class, Purchase::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
  abstract fun customerDao(): CustomerDao
  abstract fun pocketDao(): PocketDao
  abstract fun purchaseDao(): PurchaseDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "gold_pocket"
        )
//          .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
