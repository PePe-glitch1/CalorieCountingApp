package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.DailyCaloryDao
import com.example.data.local.dao.UsersDao
import com.example.data.local.entity.DailyCalory
import com.example.data.local.entity.Users
import android.content.Context
import androidx.room.Room

@Database(
    entities = [
        Users::class,
        DailyCalory::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun dailyCaloryDao(): DailyCaloryDao

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
                .fallbackToDestructiveMigration()  // Тільки для розробки!
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}