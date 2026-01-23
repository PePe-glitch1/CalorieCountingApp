package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.local.entity.AllProductsEntity

@Dao
interface AllProductsDao {

    @Query("SELECT * FROM all_products WHERE product_name = :productName")
    suspend fun getUserParamsByUserId(productName: String): AllProductsEntity?

}