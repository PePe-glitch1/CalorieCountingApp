package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.DailyCaloryEntity

@Dao
interface DailyCaloryDao {

    @Insert
    suspend fun insert(dailyCalory: DailyCaloryEntity): Long

    @Update
    suspend fun update(dailyCalory: DailyCaloryEntity)

    @Query("SELECT * FROM daily_calory WHERE parent_id = :parentId")
    suspend fun getDailyCaloryByParentId(parentId: Long): DailyCaloryEntity?

    @Query("SELECT * FROM daily_calory WHERE daily_calory_id = :id")
    suspend fun getDailyCaloryById(id: Long): DailyCaloryEntity?

    @Query("DELETE FROM daily_calory WHERE daily_calory_id = :id")
    suspend fun delete(id: Long)

}