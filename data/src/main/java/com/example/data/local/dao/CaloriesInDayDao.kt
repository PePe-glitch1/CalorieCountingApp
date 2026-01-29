package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.CaloriesInDayEntity
import java.time.LocalDate

@Dao
interface CaloriesInDayDao {

    @Insert
    suspend fun insert(caloriesInDay: CaloriesInDayEntity): Long

    @Query("SELECT * FROM calories_in_day WHERE user_id = :userId ")
    suspend fun getCaloriesInDayByUserIdAndDate(userId: Long ): CaloriesInDayEntity?

    @Update
    suspend fun update(caloriesInDay: CaloriesInDayEntity)

    @Query("DELETE FROM calories_in_day WHERE calories_in_day_id = :id")
    suspend fun deleteById(id: Long)

}