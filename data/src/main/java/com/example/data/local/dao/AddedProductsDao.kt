package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.entity.AddedProductsEntity
import java.time.LocalDateTime

@Dao
interface AddedProductsDao {

    @Insert
    suspend fun insert(addedProducts: AddedProductsEntity): Long

    @Query("SELECT * FROM added_products WHERE user_id = :userId")
    suspend fun getAddedProductsByUserId(userId: Long): List<AddedProductsEntity>

    @Query("SELECT * FROM added_products WHERE user_id = :userId AND date = :date")
    suspend fun getAddedProductsByUserIdAndData(userId: Long, date: LocalDateTime): List<AddedProductsEntity>

    @Query("SELECT * FROM added_products WHERE added_products_id = :id")
    suspend fun getAddedProductsById(id: Long): AddedProductsEntity?

    @Query("DELETE FROM added_products WHERE added_products_id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM added_products WHERE user_id = :userId AND date BETWEEN :from AND :to")
    suspend fun getAddedProductsByUserIdInRange(userId: Long, from: LocalDateTime, to: LocalDateTime): List<AddedProductsEntity>

}