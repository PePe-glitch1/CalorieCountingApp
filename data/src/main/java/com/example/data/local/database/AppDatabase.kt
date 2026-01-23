package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.UserDao
import com.example.data.local.dao.DailyCaloryDao
import com.example.data.local.dao.UserParamsDao
import com.example.data.local.entity.UserEntity
import com.example.data.local.entity.DailyCaloryEntity
import com.example.data.local.entity.UserParamsEntity
import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.data.local.convert.Converters
import com.example.data.local.dao.AddedProductsDao
import com.example.data.local.dao.AddedVoterDao
import com.example.data.local.dao.AllProductsDao
import com.example.data.local.dao.CaloriesInDayDao
import com.example.data.local.dao.VoterInDayDao
import com.example.data.local.entity.AddedProductsEntity
import com.example.data.local.entity.AddedVoterEntity
import com.example.data.local.entity.AllProductsEntity
import com.example.data.local.entity.CaloriesInDayEntity
import com.example.data.local.entity.VoterInDayEntity

@Database(
    entities = [
        UserEntity::class,
        UserParamsEntity::class,
        DailyCaloryEntity::class,
        AllProductsEntity::class,
        AddedProductsEntity::class,
        CaloriesInDayEntity::class,
        VoterInDayEntity::class,
        AddedVoterEntity::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userParamsDao(): UserParamsDao
    abstract fun dailyCaloryDao(): DailyCaloryDao
    abstract fun allProductsDao(): AllProductsDao
    abstract fun addedProductsDao(): AddedProductsDao
    abstract fun caloriesInDayDao(): CaloriesInDayDao
    abstract fun voterInDayDao(): VoterInDayDao
    abstract fun addedVoterDao(): AddedVoterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration(true)  // Тільки для розробки!
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}