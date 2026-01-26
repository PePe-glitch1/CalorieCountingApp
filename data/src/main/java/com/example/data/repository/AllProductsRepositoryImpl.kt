package com.example.data.repository

import com.example.data.local.dao.AllProductsDao
import com.example.data.local.mapper.toDomain
import com.example.domain.models.AllProducts
import com.example.domain.repository.AllProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AllProductsRepositoryImpl(
    private val allProductsDao: AllProductsDao
): AllProductsRepository {

    override suspend fun searchProducts(query: String): Flow<List<AllProducts>> {
        return allProductsDao.searchProducts(query)
            .map { entities -> entities.toDomain() }
    }
}