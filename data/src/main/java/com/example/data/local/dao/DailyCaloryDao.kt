package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.DailyCaloryEntity

@Dao
interface DailyCaloryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dailyCalory: DailyCaloryEntity): Long

    @Update
    suspend fun update(dailyCalory: DailyCaloryEntity)

    @Query("DELETE FROM daily_calory WHERE daily_calory_id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM daily_calory WHERE parent_id = :parentId")
    suspend fun getDailyCaloryByParentId(parentId: Long): DailyCaloryEntity?

    @Delete
    suspend fun delete(dailyCalory: DailyCaloryEntity)

}