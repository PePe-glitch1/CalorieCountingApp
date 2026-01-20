package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.Users

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert (user: Users): Long

    @Update
    suspend fun update(user: Users)

    @Query("SELECT * FROM users_info WHERE user_id = :id")
    suspend fun getUserById(id: Long): Users?

    @Delete
    suspend fun delete(id: Long)

}