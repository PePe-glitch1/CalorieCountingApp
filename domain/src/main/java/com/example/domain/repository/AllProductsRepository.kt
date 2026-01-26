package com.example.domain.repository

import com.example.domain.models.AllProducts
import kotlinx.coroutines.flow.Flow


interface AllProductsRepository {

    suspend fun searchProducts(query: String): Flow<List<AllProducts>>

}