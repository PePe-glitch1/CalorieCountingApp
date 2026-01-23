package com.example.domain.repository

import com.example.domain.models.AddedProducts
import java.time.LocalDateTime

interface AddedProductsRepository {

    suspend fun saveAddedProduct(params: AddedProducts): Long

    suspend fun getAllAddedProductByUserId(userId: Long): List<AddedProducts>?

    suspend fun getAllAddedProductByUserIdAndDate(userId: Long, date: LocalDateTime): List<AddedProducts>?

    suspend fun getAddedProductById(id: Long): AddedProducts?

    suspend fun deleteAddedProductById(id: Long)

}