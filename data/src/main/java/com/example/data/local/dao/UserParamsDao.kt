package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.UserParamsEntity

@Dao
interface UserParamsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert (user: UserParamsEntity): Long

    @Update
    suspend fun update(user: UserParamsEntity)

    @Query("SELECT * FROM users_params WHERE user_id = :id")
    suspend fun getUserParamsById(id: Long): UserParamsEntity?

    @Query("DELETE FROM users_params WHERE user_id = :id")
    suspend fun deleteById(id: Long)

    @Delete
    suspend fun delete(user: UserParamsEntity)

}