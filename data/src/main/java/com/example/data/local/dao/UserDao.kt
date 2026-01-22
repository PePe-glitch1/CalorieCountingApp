package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.data.local.entity.User
import com.example.data.local.entity.UserWithUserParams


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert (user: User): Long

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM users WHERE user_id = :id")
    suspend fun getUserById(id: Long): User?

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :id")
    suspend fun getUserWithParamsById(id: Long): UserWithUserParams?

    @Query("DELETE FROM users WHERE user_id = :id")
    suspend fun deleteById(id: Long)

    @Delete
    suspend fun delete(user: User)

}