package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.VoterInDayEntity

@Dao
interface VoterInDayDao {

    @Insert
    suspend fun insert(voterInDay: VoterInDayEntity): Long

    @Query("SELECT * FROM voter_in_day WHERE user_id = :userId AND date = :date")
    suspend fun getVoterInDayByUserIdAndDate(userId: Long, date: String): VoterInDayEntity?

    @Update
    suspend fun update(voterInDay: VoterInDayEntity)

    @Query("DELETE FROM voter_in_day WHERE voter_in_day_id = :id")
    suspend fun deleteById(id: Long)

}