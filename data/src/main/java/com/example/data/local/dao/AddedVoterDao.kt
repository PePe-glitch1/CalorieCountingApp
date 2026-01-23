package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.entity.AddedVoterEntity

@Dao
interface AddedVoterDao {

    @Insert
    suspend fun insert(addedVoter: AddedVoterEntity): Long

    @Query("SELECT * FROM added_voter WHERE user_id = :userId")
    suspend fun getAddedVoterByUserId(userId: Long): List<AddedVoterEntity>?

    @Query("SELECT * FROM added_voter WHERE user_id = :userId AND date = :date")
    suspend fun getAddedVoterByUserIdAndDate(userId: Long, date: String): List<AddedVoterEntity>?

    @Query("SELECT * FROM added_voter WHERE added_voter_id = :id")
    suspend fun getAddedVoterById(id: Long): AddedVoterEntity?

    @Query("DELETE FROM added_voter WHERE added_voter_id = :id")
    suspend fun deleteById(id: Long)

}