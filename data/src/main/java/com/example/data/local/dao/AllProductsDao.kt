package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.local.entity.AllProductsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AllProductsDao {

    @Query("""
        SELECT * FROM  all_products
        WHERE product_name LIKE '%' || :searchQuery || '%'
        ORDER BY product_name ASC
    """)
    fun searchProducts(searchQuery: String): Flow<List<AllProductsEntity>>

}